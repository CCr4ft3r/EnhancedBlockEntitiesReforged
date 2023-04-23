# Enhanced Block Entities Reforged (Unofficial)
EBE Reforged is an unofficial Forge port of [Enhanced Block Entities](https://legacy.curseforge.com/minecraft/mc-mods/enhanced-block-entities).  
This mod gives you a huge fps improvement for rendering block entities like chests: Approx. 3 times more fps with shaders and about 8 times without it.  
This mod requires [Reforgium](https://modrinth.com/mod/reforgium) and [BRRP <= 0.9.0](https://www.curseforge.com/minecraft/mc-mods/better-runtime-resource-pack/files)

### Important Notes and Requests
The original copyright owner is **FoundationGames** who created the Fabric version of this port.

- Please do not report issues with this port to the original owner or to their GitHub repo. Please report it to [mine](https://github.com/CCr4ft3r/EnhancedBlockEntitiesReforged/issues).
- Please do not join their discord or post a comment on their mod page in order to get support for my port.
- Please post your questions here.

This port currently does not change any of the original optimizations. On code side the only differences are the following ones:

- I adapted the class references and mixin injection points to match it to SRG and Forge
- I removed config screen related stuff since they were based on another fabric mod.

This port is fully compatible with Rubidium and Oculus.

### How it works
EBE makes some block entities use baked block models rather than laggy entity models.

### Comparison
Rendering 1700 chests in a flat world:
![fps comparision](https://i.imgur.com/vFZzhue.png)