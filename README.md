# AutoCompleter
Java Swings Text Field Auto Completer Library

This is the small library built for java swings developers who may need the auto suggestions and auto complete feature for this app. Currently I have implemented this feature for the JtextField component.

MAutoCompleter is easy to use and have all required functionality for the developer.

Library(jar file) found here: dist/TextFieldAutoCompleter.jar
Just download the jar, add to the built path and code.

Code Example: There is test class (JFrame) you can refer.

MAutoCompleter mTextFieldAutoCompleter = new MTextFieldAutoCompleter(jTextField1);
List<Object> items = new ArrayList<>();
items.add("Apple");
items.add("Orange");
items.add("Mango");
items.add("Grapes");
items.add("Banana");
items.add("Guava");
mTextFieldAutoCompleter.addItems(items);
mTextFieldAutoCompleter.setNumOfItems(15);//optional; we have default one
mTextFieldAutoCompleter.setShowSuggestionBoxAtBegin(true);//optional; by default its true
mTextFieldAutoCompleter.configure();

You are done!
        
