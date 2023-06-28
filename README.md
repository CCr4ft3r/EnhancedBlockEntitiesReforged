# Enhanced Block Entities Reforged (Unofficial)
EBE Reforged is an unofficial Forge port of [Enhanced Block Entities](https://legacy.curseforge.com/minecraft/mc-mods/enhanced-block-entities).  
This mod gives you a huge fps improvement for rendering block entities like chests: Approx. 3 times more fps with shaders and about 8 times without it.
Including other block entities like ender chests, beds, shulker boxes, signs and bells.
This mod requires [Reforgium](https://www.curseforge.com/minecraft/mc-mods/reforgium)

[![](https://img.shields.io/discord/1087408974271357040?color=7289DA&label=Discord&logo=discord&logoColor=FFFFFF '')](https://discord.com/invite/TEub2vQfhF) Join my Discord for support, suggestions and discussion with others

### License and copyright notice
This mod contains the Better Runtime Resource Pack mod licensed under Mozilla Public License 2.0. The original and copyright owner is [SolidBlock_](https://legacy.curseforge.com/members/SolidBlock_/projects)
Project: [BRRP Mod page](https://www.curseforge.com/minecraft/mc-mods/better-runtime-resource-pack)
Source code: [GitHub Repo](https://github.com/SolidBlock-cn/BRRP)

The original copyright owner of this port are
- **FoundationGames** who created the Fabric version of this port licensed under LGPL-3.0 license
- **FabricMC and contributors** who provided functionality for custom model loading licensed under Apache License 2.0

### Important Notes and Requests
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