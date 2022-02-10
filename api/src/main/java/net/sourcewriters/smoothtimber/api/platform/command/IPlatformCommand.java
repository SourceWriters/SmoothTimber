package net.sourcewriters.smoothtimber.api.platform.command;

import com.syntaxphoenix.avinity.command.node.RootNode;
import com.syntaxphoenix.avinity.module.extension.ExtensionPoint;
import com.syntaxphoenix.avinity.module.extension.IExtension;

@ExtensionPoint
public interface IPlatformCommand extends IExtension {
    
    RootNode<PlatformSource> build(String name) throws Exception;

}
