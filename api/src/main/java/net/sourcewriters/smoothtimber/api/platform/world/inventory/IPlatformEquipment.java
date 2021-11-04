package net.sourcewriters.smoothtimber.api.platform.world.inventory;

// TODO(Lauriichan): Add documentation

public interface IPlatformEquipment {

    IPlatformItem getMainHand();

    void setMainHand(IPlatformItem item);

    IPlatformItem getOffHand();

    void setOffHand(IPlatformItem item);

    IPlatformItem getHelmet();

    void setHelmet(IPlatformItem item);

    IPlatformItem getChestplate();

    void setChestplate(IPlatformItem item);

    IPlatformItem getLeggings();

    void setLeggings(IPlatformItem item);

    IPlatformItem getBoots();

    void setBoots(IPlatformItem item);

}
