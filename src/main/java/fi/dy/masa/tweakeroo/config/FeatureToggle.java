package fi.dy.masa.tweakeroo.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.IConfigBoolean;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyCallbackToggleBooleanConfigWithMessage;
import fi.dy.masa.malilib.hotkeys.KeybindMulti;
import fi.dy.masa.malilib.util.StringUtils;
import fi.dy.masa.tweakeroo.LiteModTweakeroo;

public enum FeatureToggle implements IConfigBoolean, IHotkey
{
    CARPET_FLEXIBLE_BLOCK_PLACEMENT ("carpetFlexibleBlockPlacement",        false, "LMENU,C", "If enabled, then the flexible block placement uses the protocol\nimplemented in the recent carpet mod versions", "Carpet protocol Flexible Placement"),
    FAST_PLACEMENT_REMEMBER_ALWAYS  ("fastPlacementRememberOrientation",    true, "LSHIFT,X,F", "If enabled, then the fast placement mode will always remember\nthe orientation of the first block you place.\nWithout this, the orientation will only be remembered\nwith the flexible placement enabled and active.", "Fast Placement Remember Orientation"),
    REMEMBER_FLEXIBLE               ("rememberFlexibleFromClick",           true, "LSHIFT,X,L", "If enabled, then the flexible block placement status will be remembered\nfrom the first placed block, as long as the use key is held down.", "Remember Flexible Orientation From First Click"),
    TWEAK_AFTER_CLICKER             ("tweakAfterClicker",                   false, "X,C", "Enables a \"after clicker\" tweak, which does automatic right clicks on the just-placed block.\nUseful for example for Repeaters (setting the delay)."),
    TWEAK_FAST_BLOCK_PLACEMENT      ("tweakFastBlockPlacement",             false, "X,F", "Enables fast/convenient block placement when moving the cursor over new blocks"),
    TWEAK_FAST_LEFT_CLICK           ("tweakFastLeftClick",                  false, "X,Y", "Enables automatic fast left clicking while holding down the attack button (left click).\nThe number of clicks per game tick is set in the Generic configs."),
    TWEAK_FAST_RIGHT_CLICK          ("tweakFastRightClick",                 false, "X,U", "Enables automatic fast right clicking while holding down the use button (right click).\nThe number of clicks per game tick is set in the Generic configs."),
    TWEAK_FLEXIBLE_BLOCK_PLACEMENT  ("tweakFlexibleBlockPlacement",         false, "X,L", "Enables placing blocks in different orientations while holding down the keybind"),
    TWEAK_GAMMA_OVERRIDE            ("tweakGammaOverride",                  false, "X,G", "Overrides the video settings gamma value with the one set in the Generic configs"),
    TWEAK_HAND_RESTOCK              ("tweakHandRestock",                    false, "X,E", "Enables swapping a new stack to the main or the offhand when the previous stack runs out"),
    TWEAK_HOTBAR_SWAP               ("tweakHotbarSwap",                     false, "X,H", "Enables the hotbar swapping feature"),
    TWEAK_INVENTORY_PREVIEW         ("tweakInventoryPreview",               false, "X,I", "Enables an inventory preview while having the cursor over a block\nwith an inventory and holding the configured modifier key for it"),
    TWEAK_ITEM_UNSTACKING_PROTECTION("tweakItemUnstackingProtection",       false, "X,P", "If enabled, then items configured in Generic -> unstackingItems won't be\nallowed to spill out when using. This is meant for example to\nprevent throwing buckets into lava when filling them."),
    TWEAK_LAVA_VISIBILITY           ("tweakLavaVisibility",                 false, "X,A", "If enabled and the player has a Respiration helmet and/or Wather Breathing\nactive, the lava fog is greatly reduced"),
    TWEAK_MOVEMENT_KEYS             ("tweakMovementKeysLast",               false, "X,M", "If enabled, then opposite movement keys won't cancel each other,\nbut instead the last pressed key is the active input"),
    TWEAK_PLAYER_INVENTORY_PEEK     ("tweakPlayerInventoryPeek",            false, "X,Q", "Enables a player inventory peek/preview, while holding the\nconfigured modifier key for it"),
    TWEAK_NO_FALLING_BLOCK_RENDER   ("tweakNoFallingBlockEntityRendering",  false, "",    "If enabled, then falling block entities won't be rendered at all"),
    TWEAK_NO_ITEM_SWITCH_COOLDOWN   ("tweakNoItemSwitchRenderCooldown",     false, "",    "If true, then there won't be any cooldown/equip animation\nwhen switching the held item or using the item."),
    TWEAK_NO_LIGHT_UPDATES          ("tweakNoLightUpdates",                 false, "X,N", "If enabled, disables client-side light updates"),
    TWEAK_PERMANENT_SNEAK           ("tweakPermanentSneak",                 false, "LSHIFT,X,S", "If enabled, the player will be sneaking the entire time"),
    TWEAK_PICK_BEFORE_PLACE         ("tweakPickBeforePlace",                false, "X,K", "If enabled, then before each block placement, the same block\nis switched to hand that you are placing against"),
    TWEAK_SHULKERBOX_STACKING       ("tweakEmptyShulkerBoxesStack",         false, "",    "Enables empty Shulker Boxes stacking up to 64"),
    TWEAK_SWAP_ALMOST_BROKEN_TOOLS  ("tweakSwapAlmostBrokenTools",          false, "X,W", "If enabled, then any damageable items held in the hand that are\nabout to break will be swapped to fresh ones");

    private final String name;
    private final String comment;
    private final String prettyName;
    private final IKeybind keybind;
    private final boolean defaultValueBoolean;
    private boolean valueBoolean;
    private IFeatureCallback callback;

    private FeatureToggle(String name, boolean defaultValue, String defaultHotkey, String comment)
    {
        this(name, defaultValue, defaultHotkey, comment, StringUtils.splitCamelCase(name.substring(5)));
    }

    private FeatureToggle(String name, boolean defaultValue, String defaultHotkey, String comment, String prettyName)
    {
        this.name = name;
        this.valueBoolean = defaultValue;
        this.defaultValueBoolean = defaultValue;
        this.comment = comment;
        this.prettyName = prettyName;
        this.keybind = KeybindMulti.fromStorageString(defaultHotkey);
        this.keybind.setCallback(new KeyCallbackToggleBooleanConfigWithMessage(this));
    }

    public void setCallback(IFeatureCallback callback)
    {
        this.callback = callback;
    }

    @Override
    public ConfigType getType()
    {
        return ConfigType.BOOLEAN;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public String getPrettyName()
    {
        return this.prettyName;
    }

    @Override
    public String getStringValue()
    {
        return String.valueOf(this.valueBoolean);
    }

    @Override
    public void setValueFromString(String value)
    {
    }

    @Override
    public String getComment()
    {
        return comment != null ? comment : "";
    }

    @Override
    public IKeybind getKeybind()
    {
        return this.keybind;
    }

    @Override
    public boolean getBooleanValue()
    {
        return this.valueBoolean;
    }

    @Override
    public void setBooleanValue(boolean value)
    {
        boolean oldValue = this.valueBoolean;
        this.valueBoolean = value;

        if (this.callback != null && oldValue != this.valueBoolean)
        {
            this.callback.onValueChange(this);
        }
    }

    @Override
    public boolean isModified()
    {
        return this.valueBoolean != this.defaultValueBoolean;
    }

    @Override
    public boolean isModified(String newValue)
    {
        return Boolean.parseBoolean(newValue) != this.defaultValueBoolean;
    }

    @Override
    public void resetToDefault()
    {
        this.valueBoolean = this.defaultValueBoolean;
    }

    @Override
    public JsonElement getAsJsonElement()
    {
        return new JsonPrimitive(this.valueBoolean);
    }

    @Override
    public void setValueFromJsonElement(JsonElement element)
    {
        try
        {
            if (element.isJsonPrimitive())
            {
                this.valueBoolean = element.getAsBoolean();
            }
            else
            {
                LiteModTweakeroo.logger.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element);
            }
        }
        catch (Exception e)
        {
            LiteModTweakeroo.logger.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element, e);
        }
    }
}
