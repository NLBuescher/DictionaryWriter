# Changelog

## Planned Features
- adding an app icon
- adding syntax highlighting and error marking
- adding the ability to actually edit the CSS for the dictionary (the CSS editor is currently empty and does nothing).
- adding status updates for various task to the status bar (file saved, file imported, file loaded, etc)
- adding tutorial-like help stuff to the readme or wiki
- giving the user copypasta powers for tree items

## Release v2.0.0

i rewrote the app to use a markdown-style editor instead of individual textboxes. the markdown-like syntax is based
loosely on markdown, but is custom built to fit various dictionary entry components. the markdown parser is implemented
using an `ANTLR4` grammar.

Added:
- added a use for the status bar: showing the status!

Changed:
- the app no longer reads and writes directly from and to `XHTML` files (it can import and export a dictionary from and 
    to `XHTML`), but instead saves files as `mddict`s which are just serialized `Dictionary` objects. this change is to
    preserve text formatting on save and load since the markdow syntax allows for some flexibility.
- the `TreeView` now only shows entries in the dictionary, greatly reducing the clutter.
- added `WebView`-based entry, index, and CSS editors, and removed the `TextField`-based editor entirely.
- the entry editor content is converted to xhtml with the help of an `ANTLR4` grammar and thereby generated parser
- changed the crunch `#?` modifier to a percent `%?` (the crunch was heavily used in the markdown grammar spec).
- removed the `ant` build script in favor of IntelliJ artifacts. at some point the ant script actually broke and i had
    no idea why, so after spending a few hours trying to fix it, i gave up and replaced it. (the issue seemed to be with
    the appbundler step).
- adding a new entry to the dictionary will now place the new entry directly underneath the currently selected entry.
    - should investigate what happens when no entry is selected (will do so for next patch).

API changes:
- organized the mehthods in the controller class first by `public` v `private`, then by `@FXML` declaration, then
    alphabetically.


## Release v1.2.0

Added:
- rearrangeable items in the treeview can now be moved up and down

Bug Fixes:
- fixed a bug where the TextArea in the Note element editor was too big for the editor and would scroll entryText
    instead of wrap it
- fixed a bug where the WebView wouldn't render the `font-weight: 600;` attribute for spans. i changed it to
    `font-weight: bold;` and it works fine now

Changed:
- moved indices to the DictEntry editor view to save space in the main treeview.
- changed the double at `@@?` modifier to a single crunch `#?` (the @ took up too much space in the entryText fields)

API changes:
- organized the methods in the Controller class, first by `public` vs. `private`, then alphabetically


## Release v1.1.0

Bug Fixes:
- fixed a bug where the sub-entry labels and sub-entry texts weren't being written to file

Changed:
- the preview and editor tabs now share the canvas, because switching between them was unnecessary and annoying
- the preview now updates with the updating of any entryText field in addition to updating with the selection of a
    different tree item
- the preview has been moved to the center, providing a sort of WYSIWYG experience.
- minor cosmetic updates like adding a space after certain elements, adding an em-dash (—) instead of en-dash (–) before
    the example translation, etc.


## Release v1.0.0

What it can do so far:
- load a dictionary from an xhtml or xml file
- create a new dictionary and save it to xhtml or xml
- save a loaded dictionary to xhtml or xml
- preview a loaded dictionary with css and xsl stylesheets that are by default called `style.css` and `webtransform.xsl`
    (no way to change this yet)
- preview a new dictionary with the included `style.css` and `webtransform.xsl` (not sure if there will be a way to
    change this)
- to avoid having to type tags in the entryText fields, the app supports a selection of markdown syntax which is
    automatically replaced with the proper tags when saving. These so far include: `**bold**`, `*italic*`,
    `***bold italic***`, and `^(superscripts)`.
- the double at sign `@@?` is a special modifier I came up with to denote characters that are changed based on an xsl
    file and are converted to `<span id="?"/>` when saving.
