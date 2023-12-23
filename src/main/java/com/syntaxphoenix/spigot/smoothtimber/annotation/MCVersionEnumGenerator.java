package com.syntaxphoenix.spigot.smoothtimber.annotation;

import static java.lang.String.format;
import static java.lang.String.join;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import org.bukkit.Bukkit;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.EnumConstantSource;
import org.jboss.forge.roaster.model.source.JavaEnumSource;

import com.syntaxphoenix.spigot.smoothtimber.version.Version;
import com.syntaxphoenix.spigot.smoothtimber.version.VersionManager;
import com.syntaxphoenix.spigot.smoothtimber.version.VersionManager.VersionState;
import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger;

public final class MCVersionEnumGenerator extends AbstractProcessor {

    private Messager messager;

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        this.messager = processingEnv.getMessager();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(SupportedVersions.class.getName());
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            return false;
        }
        final HashMap<String, ArrayList<Version>> map = new HashMap<>();
        final ArrayList<String> types = new ArrayList<>();

        for (final Element element : roundEnv.getElementsAnnotatedWith(SupportedVersions.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                continue;
            }
            final String typeName = element.asType().toString();
            if (types.contains(typeName)) {
                continue;
            }
            final SupportedVersions versionsSupported = element.getAnnotation(SupportedVersions.class);
            types.add(typeName);
            final ArrayList<Version> versions = new ArrayList<>();
            map.put(typeName, versions);
            for (final String supported : versionsSupported.value()) {
                final Version version = Version.fromString(supported);
                if (version.major() == 0) {
                    messager.printMessage(Kind.WARNING, format("Failed to parse version '%s' of type '%s'", supported, typeName), element);
                    continue;
                }
                versions.add(version);
            }
        }

        types.sort((t1, t2) -> {
            final ArrayList<Version> v1 = map.get(t1);
            final ArrayList<Version> v2 = map.get(t2);
            final int comp = Boolean.compare(v1.isEmpty(), v2.isEmpty());
            if (comp != 0) {
                return comp;
            }
            return v1.get(0).compareTo(v2.get(0));
        });

        final JavaEnumSource source = Roaster.create(JavaEnumSource.class);
        source.setName("MCVersion");
        source.setPackage("com.syntaxphoenix.spigot.smoothtimber.version.manager.gen");
        source.addImport(Version.class);
        source.addImport(VersionManager.class);
        source.addImport(VersionState.class);
        source.addImport(VersionChanger.class);
        source.addImport(ArrayList.class);
        source.addImport(Collections.class);
        source.addImport(List.class);
        source.addImport(Bukkit.class);
        source.addNestedType(join("\n", new String[] {
            "private static interface ChangerBuilder {",
            "   public VersionChanger create();",
            "}"
        }));
        source.addNestedType(join("\n", new String[] {
            "public static final class MCVersionResult {",
            "   private final MCVersion version;",
            "   private final VersionState state;",
            "   private MCVersionResult(MCVersion version, VersionState state) {",
            "       this.version = version;",
            "       this.state = state;",
            "   }",
            "   public MCVersion version() { return version; }",
            "   public VersionState state() { return state; }",
            "}"
        }));
        source.addMethod(join("\n", new String[] {
            "MCVersion(Version[] versions, ChangerBuilder builder) {",
            "   this.builder = builder;",
            "   versionManager.set(VersionState.SUPPORTED, versions);",
            "}"
        })).setConstructor(true);
        for (int index = 0; index < types.size(); index++) {
            final String type = types.get(index);
            final ArrayList<Version> versions = map.get(type);

            source.addImport(type);

            final String typeName = getNameFromType(type);
            final String versionName = getVersionFromName(typeName);
            final EnumConstantSource enumSource = source.addEnumConstant(versionName);

            final StringBuilder versionArray = new StringBuilder("new Version[] {");
            for (final Version version : versions) {
                versionArray.append("new Version(");
                versionArray.append(version.major()).append(", ").append(version.minor()).append(", ").append(version.patch());
                versionArray.append("),");
            }
            enumSource.setConstructorArguments(versionArray.append("}").toString(), format("() -> new %s()", typeName));
        }
        source.addField("private static final MCVersion[] ALL_VERSIONS = MCVersion.values();");
        source.addField("private static Version minecraftVersion;");
        source.addField("private static MCVersionResult coreVersionResult;");

        source.addField(
            "private final VersionManager versionManager = new VersionManager();");
        source.addField("private final ChangerBuilder builder;");

        source.addMethod(join("\n", new String[] {
            "public VersionChanger create() {",
            "    return builder.create();",
            "}"
        }));

        source.addMethod(join("\n", new String[] {
            "public VersionState getState(Version version) {",
            "    return versionManager.state(version);",
            "}"
        }));

        source.addMethod(join("\n", new String[] {
            "public List<Version> getKnownSupported() {",
            "    return versionManager.get(VersionState.SUPPORTED);",
            "}"
        }));

        source.addMethod(join("\n", new String[] {
            "public static List<Version> getAllKnownSupported() {",
            "   ArrayList<Version> list = new ArrayList<>();",
            "   for (MCVersion version : ALL_VERSIONS) {",
            "       list.addAll(version.getKnownSupported());",
            "   }",
            "   return Collections.unmodifiableList(list);",
            "}"
        }));
        source.addMethod(join("\n", new String[] {
            "public static Version getMinecraftVersion() {",
            "   if (minecraftVersion != null) {",
            "       return minecraftVersion;",
            "   }",
            "   String versionStr = Bukkit.getVersion().split(\" \")[2].replace(\")\", \"\");",
            "   Version version = Version.fromString(versionStr);",
            "   if (version == null || version.major() == 0) {",
            "       throw new IllegalStateException(String.format(\"Minecraft version is invalid: '%s'\", versionStr));",
            "   }",
            "   return (minecraftVersion = version);",
            "}"
        }));
        source.addMethod(join("\n", new String[] {
            "public static MCVersion getCoreVersion() {",
            "   return getCoreVersionResult().version();",
            "}"
        }));
        source.addMethod(join("\n", new String[] {
            "public static MCVersionResult getCoreVersionResult() {",
            "   if (coreVersionResult != null) {",
            "       return coreVersionResult;",
            "   }",
            "   return (coreVersionResult = detectCoreVersion(getMinecraftVersion()));",
            "}"
        }));
        source.addMethod(join("\n", new String[] {
            "public static MCVersionResult detectCoreVersion(Version version) {",
            "   if (ALL_VERSIONS.length == 0) {",
            "       throw new IllegalStateException(\"No core version available\");",
            "   }",
            "   MCVersion selected = ALL_VERSIONS[0];",
            "   VersionState selectedState = VersionState.UNSUPPORTED;",
            "   for (MCVersion current : ALL_VERSIONS) {",
            "       VersionState state = current.getState(version);",
            "       if (state == VersionState.INCOMPATIBLE) {",
            "           break;",
            "       }",
            "       selected = current;",
            "       if ((selectedState = state) == VersionState.SUPPORTED || state == VersionState.UNSUPPORTED) {",
            "           break;",
            "       }",
            "   }",
            "   return new MCVersionResult(selected, selectedState);",
            "}"
        }));

        try {
            final JavaFileObject file = processingEnv.getFiler().createSourceFile(source.getPackage() + '.' + source.getName());
            try (Writer writer = file.openWriter()) {
                writer.write(source.toString());
            }
        } catch (final IOException e) {
        }

        return false;
    }

    /*
     * Helper
     */

    private String getNameFromType(final String typeName) {
        final String[] parts = typeName.split("\\.");
        return parts[parts.length - 1];
    }

    private String getVersionFromName(final String changerName) {
        return changerName.split("x", 2)[0] + 'x';
    }

}
