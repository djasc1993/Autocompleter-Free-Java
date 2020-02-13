# AutoCompleter Free
## Description
@mudassirbit: "This is the small library built for java swings developers who may need the auto suggestions and auto complete feature for this app. Currently I have implemented this feature for the JtextField component"

## Introduction
Java Swings Text Field Auto Completer Library Created for @mudassirbit and Modified for @djasc1993
Library(jar file) found here: dist/TextFieldAutoCompleter.jar
Just download the jar, add to the built path and code.
You are done!
## Changues
En la version `AutoCompleter 2.0.0` se ha realizado
- [x] Created new repository
- [x] Changes in Class Names
- [x] Add new Class (Theme and AutoCallback)
- [x] Redesign new Test
- [x] Fix Bugs Minors
- [ ] Fix new Bugs

#### Plus
- Protected methods
- Deprecated methods
- Add new functions such as [Contain Text, Add Separator, Apply themes, auto hide JPanel, implement Aucallback class]
- Create new search methods
- Allow to add objects directly without inserting them into lists
- Create function 'pDebug' to print in console whenever Autocompleter.DEBUG = true;

## Install
The installation of Autocompleter Free 2.0.0 is quite simple.
### Basic
You only receive the JTextField to start the Autocompletor.

```
//AutoCompleterExtra.DEBUG = true;
autoComplete = new AutoCompleter(jTextField1);
autoComplete.setMethodSearch(AutoCompleterInterface.MODE_SEARCH_CHAR_CONTAINS); // default MODE_SEARCH_CHAR_CONTAINS
List<Object> items = new ArrayList<>();
items.add("Apple");
items.add("Orange");
items.add("Mango");
autoComplete.addItems(items);
```
### Advanced

In this constructor you can send a custom Theme for the background of the Popup and Text Color or Send null for Color by default, and finally you can send an AutoCallback to execute custom actions of the inserted objects.
```
//AutoCompleterExtra.DEBUG = true;
autoComplete = new AutoCompleter(jTextField1,new Theme(Color.LIGHT_GRAY, Color.DARK_GRAY),(Object object_select) -> {
        ((ObjectCustom)  object_select).executeAction();
});
autoComplete.setMethodSearch(AutoCompleterInterface.MODE_SEARCH_CHAR_CONTAINS); // default MODE_SEARCH_CHAR_CONTAINS
autoComplete.setConcatSearch(true); // default false
autoComplete.setCharSeparate(", ");  // default ""

autoComplete.addItem(new ObjectCustom("APPLE UNO","ejecuta APPLE"));
autoComplete.addItem(new ObjectCustom("HOME DOS","ejecuta HOME"));
autoComplete.addItem(new ObjectCustom("HOME UNO","ejecuta HOME"));
autoComplete.addItem(new ObjectCustom("TOMATO","ejecuta TOMATO"));
```

## Wiki
Can found wiki in [Wiki AutoCompleter Free](https://github.com/djasc1993/AutocompletorJava/wiki).

## AutoCompleter Free: Spanish - Español
### Description
@mudassirbit: "Esta es la pequeña biblioteca creada para desarrolladores de Java Swings que pueden necesitar las sugerencias automáticas y la función de autocompletar para esta aplicación. Actualmente he implementado esta característica para el componente JtextField".
### Introduction
Biblioteca (archivo jar) que se encuentra aquí: dist / TextFieldAutoCompleter.jar
Simplemente descargue el jar, agréguelo a la ruta y el código construidos.
¡Estás listo!
 

