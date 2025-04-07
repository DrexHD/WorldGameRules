# WorldGameRules
A fabric mod to change all gamerules to be separate per world

## Commands
The vanilla gamerule command has been replaced with a custom command that requires a dimension.
The gamerule command requires `world-gamerules.commands.gamerule` or operator level 2.

- `/gamerule <rule> [dimension selector]` - Query the gamerule `<rule>` in all specified dimensions
- `/gamerule <rule> <value> [dimension selector]` - Set the gamerule `<rule>` to `<value>` in all specified dimensions

### Dimension selectors
Dimension selectors allow you to specify what dimensions should be queried / set
- `@all` - Selects all dimensions
- `@namespace <namespace>` - Selects dimensions with the specified namespace (eg. namespace `minecraft` selects `minecraft:overworld`, `minecraft:the_nether` and `minecraft:the_end`, but not `custom:dimension`)
- `@regex <regex>` - Selects all dimensions, where the dimension id matches the [regex](https://regex101.com/)
- `<dimension id>` - To select a specific dimension

## Misc
To implement `doDaylightCycle` and `doWeatherCycle` gamerules this mod allows worlds to have separate, independent weather and day cycles.