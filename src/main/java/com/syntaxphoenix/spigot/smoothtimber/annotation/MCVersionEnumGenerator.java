package com.syntaxphoenix.spigot.smoothtimber.annotation;

import static java.lang.String.format;
import static java.lang.String.join;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.EnumConstantSource;
import org.jboss.forge.roaster.model.source.JavaEnumSource;

import com.syntaxphoenix.syntaxapi.version.DefaultVersion;
import com.syntaxphoenix.syntaxapi.version.Version;
import com.syntaxphoenix.syntaxapi.version.VersionAnalyzer;

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

        final VersionAnalyzer analyzer = new DefaultVersion().getAnalyzer();
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
                final Version version = analyzer.analyze(supported);
                if (version.getMajor() == 0) {
                    messager.printMessage(Kind.WARNING, format("Failed to parse version '%s' of type '%s'", supported, typeName), element);
                    continue;
                }
                versions.add(analyzer.analyze(supported));
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
        source.addImport("com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger");
        source.addImport("com.syntaxphoenix.syntaxapi.version.DefaultVersion");
        source.addImport("com.syntaxphoenix.syntaxapi.version.Version");
        source.addImport("com.syntaxphoenix.syntaxapi.version.VersionAnalyzer");
        source.addImport("com.syntaxphoenix.syntaxapi.version.VersionManager");
        source.addImport("com.syntaxphoenix.syntaxapi.version.VersionState");
        source.addImport("java.util.ArrayList");
        source.addImport("org.bukkit.Bukkit");
        source.addNestedType(join("\n", "private static interface ChangerBuilder {", "   public VersionChanger create();", "}"));
        source.addNestedType(join("\n", "public static final class MCVersionResult {", "   private final MCVersion version;",
            "   private final VersionState state;", "   private MCVersionResult(MCVersion version, VersionState state) {",
            "       this.version = version;", "       this.state = state;", "   }", "   public MCVersion version() { return version; }",
            "   public VersionState state() { return state; }", "}"));
        source.addMethod(join("\n", "MCVersion(DefaultVersion[] versions, ChangerBuilder builder) {", "   this.builder = builder;",
            "   versionManager.setAll(VersionState.SUPPORTED, versions);", "}")).setConstructor(true);
        for (int index = 0; index < types.size(); index++) {
            final String type = types.get(index);
            final ArrayList<Version> versions = map.get(type);

            source.addImport(type);

            final String typeName = getNameFromType(type);
            final String versionName = getVersionFromName(typeName);
            final EnumConstantSource enumSource = source.addEnumConstant(versionName);

            final StringBuilder versionArray = new StringBuilder("new DefaultVersion[] {");
            for (final Version version : versions) {
                versionArray.append("new DefaultVersion(");
                versionArray.append(version.getMajor()).append(", ").append(version.getMinor()).append(", ").append(version.getPatch());
                versionArray.append("),");
            }
            enumSource.setConstructorArguments(versionArray.append("}").toString(), format("() -> new %s()", typeName));
        }
        source.addField("private static final VersionAnalyzer ANALYZER = new DefaultVersion().getAnalyzer();");
        source.addField("private static final MCVersion[] ALL_VERSIONS = MCVersion.values();");
        source.addField("private static DefaultVersion minecraftVersion;");
        source.addField("private static MCVersionResult coreVersionResult;");

        source.addField(
            "private final VersionManager versionManager = new VersionManager(VersionState.NOT_COMPATIBLE, VersionState.NOT_TESTED, VersionState.NOT_COMPATIBLE);");
        source.addField("private final ChangerBuilder builder;");

        source.addMethod(join("\n", "public VersionChanger create() {", "    return builder.create();", "}"));

        source.addMethod(
            join("\n", "public VersionState getState(DefaultVersion version) {", "    return versionManager.getState(version);", "}"));

        source.addMethod(join("\n", "public ArrayList<DefaultVersion> getKnownSupported() {",
            "    return versionManager.getVersions(VersionState.SUPPORTED);", "}"));

        source.addMethod(join("\n", "public static ArrayList<DefaultVersion> getAllKnownSupported() {",
            "   ArrayList<DefaultVersion> list = new ArrayList<>();", "   for (MCVersion version : ALL_VERSIONS) {",
            "       list.addAll(version.getKnownSupported());", "   }", "   return list;", "}"));
        source.addMethod(join("\n", "public static DefaultVersion getMinecraftVersion() {", "   if (minecraftVersion != null) {",
            "       return minecraftVersion;", "   }", "   String versionStr = Bukkit.getVersion().split(\" \")[2].replace(\")\", \"\");",
            "   Version version = ANALYZER.analyze(versionStr);",
            "   if (version == null || version.getMajor() == 0 || !(version instanceof DefaultVersion)) {",
            "       throw new IllegalStateException(String.format(\"Minecraft version is invalid: '%s'\", versionStr));", "   }",
            "   return (minecraftVersion = (DefaultVersion) version);", "}"));
        source.addMethod(join("\n", "public static MCVersion getCoreVersion() {", "   return getCoreVersionResult().version();", "}"));
        source.addMethod(join("\n", "public static MCVersionResult getCoreVersionResult() {", "   if (coreVersionResult != null) {",
            "       return coreVersionResult;", "   }", "   return (coreVersionResult = detectCoreVersion(getMinecraftVersion()));", "}"));
        source.addMethod(join("\n", "public static MCVersionResult detectCoreVersion(DefaultVersion version) {",
            "   if (ALL_VERSIONS.length == 0) {", "       throw new IllegalStateException(\"No core version available\");", "   }",
            "   MCVersion selected = ALL_VERSIONS[0];", "   VersionState selectedState = VersionState.NOT_COMPATIBLE;",
            "   for (MCVersion current : ALL_VERSIONS) {", "       VersionState state = current.getState(version);",
            "       if (state == VersionState.NOT_COMPATIBLE) {", "           break;", "       }", "       selected = current;",
            "       if ((selectedState = state) == VersionState.SUPPORTED || state == VersionState.NOT_SUPPORTED) {", "           break;",
            "       }", "   }", "   return new MCVersionResult(selected, selectedState);", "}"));

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
