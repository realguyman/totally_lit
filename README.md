# ![Totally Lit](https://cdn.modrinth.com/data/hbKDMT6l/images/35f50553a0aa8b2382585227b402547e6db24bd3.png)

Some of Minecraft's light sources will extinguish under various conditions. An open source mod built for [Fabric](https://fabricmc.net/) (works on [Quilt](https://quiltmc.org/), too). Requires [Fabric API](https://modrinth.com/mod/fabric-api) and [owo-lib](https://modrinth.com/mod/owo-lib).

For more information regarding the project, [read the wiki 📖](https://github.com/realguyman/totally_lit/wiki).

## ✨ Features

- 💡 **Dynamic lighting is fully supported** with [LambDynamicLights](https://modrinth.com/mod/lambdynamiclights).
- 🌧 Candles and torches will **extinguish when rained on**.
- ⏳ Campfires, candles, jack o' lanterns, lanterns, and torches will **extinguish over time**.
- 🔥 **Bring unlit torches to life during your adventures with fire**, lava (flowing, standing, or even in a cauldron!), or flint and steel.
- 🏊 **Wading, swimming, fully submerging in water, or getting rained on** can cause jack o'lanterns, lanterns, and torches to extinguish.
- 💪 **Glowstone lanterns and torches for mid/late-game** which are waterproof and never die out.
- 📄 **Configurable with files or the settings page** provided by integration with [Mod Menu](https://modrinth.com/mod/modmenu).

### ❗ Compatibility

Should work with most mods if not all mods that don't modify torches, lanterns, campfires, candles, and jack o' lanterns in an intrusive way.

- [Hardcore Torches](https://modrinth.com/mod/hardcore-torches) is incompatible

### 📝 Todo

- Add ability for villagers to ignite extinguished light sources
- Build a proper API for developers (i.e., ~~entrypoint~~, events, ~~javadoc~~, etc.)
- Organize, clean, and otherwise re-design the codebase for better maintainability
- Implement more gametests and add testmods
- Extend inventory behaviours to mobs as well (i.e., a zombie holding a torch while in water should extinguish)
- Integrate
  with [Tips](https://modrinth.com/mod/tips), [Patchouli](https://modrinth.com/mod/patchouli), [Jade](https://modrinth.com/mod/jade),
  and [Lanterns Belong on Walls](https://modrinth.com/mod/lanterns-bow)
- Attempt to find a method that doesn't impact the game in an overly negative way to extinguish light sources over time
  in inventories
- Finish wiki

## 📥 Downloads

Officially available at [CurseForge](https://www.curseforge.com/minecraft/mc-mods/totally-lit/files/all?page=1&pageSize=20), [Modrinth](https://modrinth.com/mod/totally-lit/versions), and [GitHub](https://github.com/realguyman/totally_lit/releases).

## License

Copyright (C) 2024 Dale E. Wingard, Jr.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
and a copy of GNU General Public License along with this program.  If not, see
<https://www.gnu.org/licenses/>
