# Changelog

## Planned Features
- moving tree items up or down
- figuring out what to use the status bar for
- adding tutorial-like help stuff
- consolidating the indices into the editor for the Dictionary Entry tree item because as they are they hog too much space
- giving the user copypasta powers for tree items (especially indices)
- maybe add the ability for the program to auto-generate indices based on the grammar info (because manually filling them in is a pain)
- maybe adding add buttons to the editor pane

## Release v1.1.1

Bug Fixes:
- fixed a bug where the TextArea in the Note element editor was too big for the editor and would scroll text instead of wrap it
- fixed a bug where the WebView wouldn't render the `font-weight: 600;` attribute for spans. i changed it to `font-weight: bold;` and it works fine now

Changed:
- changed the double at `@@?` modifier to a single crunch `#` (the @ took up too much space in the text fields)

## Release v1.1.0

Bug Fixes:
- fixed a bug where the sub-entry labels and sub-entry texts weren't being written to file

Changed:
- the preview and editor tabs now share the canvas, because switching between them was unnecessary and annoying
- the preview now updates with the updating of any text field in addition to updating with the selection of a different tree item
- the preview has been moved to the center, providing a sort of WYSIWYG experience.
- minor cosmetic updates like adding a space after certain elements, adding an em-dash (—) instead of en-dash (–) before the example translation, etc.

## Release v1.0.0

What it can do so far:
- load a dictionary from an xhtml or xml file
- create a new dictionary and save it to xhtml or xml
- save a loaded dictionary to xhtml or xml
- preview a loaded dictionary with css and xsl stylesheets that are by default called `style.css` and `webtransform.xsl` (no way to change this yet)
- preview a new dictionary with the included `style.css` and `webtransform.xsl` (not sure if there will be a way to change this)
- to avoid having to type tags in the text fields, the app supports a selection of markdown syntax which is automatically replaced with the proper tags when saving. These so far include: `**bold**`, `*italic*`, `***bold italic***`, and `^(superscripts)`.
- the double at sign `@@?` is a special modifier I came up with to denote characters that are changed based on an xsl file and are converted to `<span id="?"/>` when saving.
