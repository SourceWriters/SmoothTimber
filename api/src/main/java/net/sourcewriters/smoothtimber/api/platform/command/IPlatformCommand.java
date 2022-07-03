package net.sourcewriters.smoothtimber.api.platform.command;

import com.syntaxphoenix.avinity.command.node.RootNode;
import com.syntaxphoenix.avinity.module.extension.ExtensionPoint;
import com.syntaxphoenix.avinity.module.extension.IExtension;

@ExtensionPoint
public interface IPlatformCommand extends IExtension {

    /**
     * Builds the root node of the command
     * 
     * @param  name      the name of the command
     * 
     * @return           the root node of the command
     * 
     * @throws Exception if something went wrong
     */
    RootNode<PlatformSource> build(String name) throws Exception;

}
