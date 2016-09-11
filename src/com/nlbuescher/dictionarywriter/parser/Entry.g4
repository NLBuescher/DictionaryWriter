grammar Entry;

@header {package com.nlbuescher.dictionarywriter.parser;}

// LEXER RULES

WhiteSpace
    : (Newline | ' ' | '\t' ) -> skip
    ;

End
    : (Newline Newline+ | Newline EOF | EOF) -> skip
    ;

Indent
    : ( '    ' | '\t' )
    ;

Newline
    : [\r\n]
    ;

Number
    : [0-9]+
    ;

DefinitionLabel
    : '- '
    | Number '. '
    ;

Heading
    : '# ' ~[\r\n]+? ' #'
    ;

Pronunciation
    : '|' ~[\r\n|(]+? '|' '(' ~[\r\n|(]+? ')'
    ;

Grammar
    : '## ' ~[\r\n]*? ' ##'
    ;

Form
    : '{' ~[\r\n{(]+? '}' '(' ~[\r\n{(]+? ')'
    ;

DefinitionGroupHeading
    : '### ' ~[\r\n]*? ' ###'
    ;

Definition
    : DefinitionLabel ~[\r\n]*? Newline
    ;

SubDefinition
    : Indent '- ' ~[\r\n]*? Newline
    ;

SubEntryLabel
    : ~[\r\n]+? Newline ('-')+ Newline
    ;

SubEntryText
    : '\'\'\'' .*? '\'\'\''
    ;

SubEntryListItem
    : '-- ' ~[\r\n]*? Newline
    ;

Note
    : '"""' .*? '"""'
    ;

ExampleText
    : Indent? ':' ~[\r\n]*? Newline
    ;

ExampleTranslation
    : Indent? '--- ' ~[\r\n]*? Newline
    ;


// PARSER RULES

d_entry
    : headGroup? entryGroup?
    ;

headGroup
    : heading pronunciationGroup?
    ;

heading
    : Heading
    ;

pronunciationGroup
    : (Indent? pronunciation)+
    ;

pronunciation
    : Pronunciation
    ;

entryGroup
    : entry+
    ;

entry
    : grammarGroup? definitionGroup+ subEntryGroup?
    ;

grammarGroup
    : grammer formGroup?
    ;

grammer
    : Grammar
    ;

formGroup
    : form+
    ;

form
    : Form pronunciationGroup?
    ;

definitionGroup
    : (definitionGroupHeading)? definition+
    ;

definitionGroupHeading
    : DefinitionGroupHeading
    ;

definition
    : Definition exampleGroup? subDefinitionGroup?
    ;

exampleGroup
    : example+
    ;

example
    : exampleText exampleTranslation?
    ;

exampleText
    : ExampleText
    ;

exampleTranslation
    : ExampleTranslation
    ;

subDefinitionGroup
    : subDefinition+
    ;

subDefinition
    : SubDefinition exampleGroup?
    ;

subEntryGroup
    : subEntry+
    ;

subEntry
    : SubEntryLabel SubEntryText noteGroup?
    | SubEntryLabel subEntryList noteGroup?
    ;

subEntryList
    : subEntryListItem+
    ;

subEntryListItem
    : SubEntryListItem
    ;

noteGroup
    : note+
    ;

note
    : Note
    ;

