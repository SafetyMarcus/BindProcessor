# The Police
Every update you make, I'll be watching you.

The Police is an annotation library that automatically generates watchers and listeners for POJO's that will also receive updates when that object is updated from the code. This is done by Annotating a `State` object with the `@Observe` annotation and passing in a view id;

```
@Observe(R.id.hello_world)
public State<String> title = new State<String>("Hello World");
```

By using this state, whenever the title value is changed `title.setValue("Goodbye World")`, the view for `R.id.hello_world` will automatically be updated with the value. If the view happens to be an `EditText` or a `CheckBox`, the coresponding watcher class (`TextWatcher`/`CheckBox.OnCheckedChangeListener`) will automatically be created to observe user made changes and update the state variable that acts as the model for the view.

The binding is then linked by calling `<class_name>ViewBinding.watch(activity);` on the class with the annotations and then passing in the activity that contains the views. This will also manually force an update on the state with the already existing view if the state is not null. This is to make sure that the linked view is up to date when it is shown. This means that if your state was to be modified in the background, you woud be able to see the changes directly in the view and vice versa.

#Gradle
ThePolice can be included in any gradle project that has maven access by adding
`com.safetymarcus.thepolice:processing:1.0.4`

That's it! It's that simple.

#Jar
Although, if you really want (have) to do things the hard way, you can download the latest <a href="https://drive.google.com/file/d/0B9zL9B65mpLlMi14MF91NTEyakk/view?usp=sharing">JAR</a> and add it to your project. This will also require that you add `classpath 'com.neenbedankt.gradle.plugins:android-apt:1.7'` to your top level `build.gradle` and `apply plugin: 'com.neenbedankt.android-apt'` to the `build.gradle` file in your module.
