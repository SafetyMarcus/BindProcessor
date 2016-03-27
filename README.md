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
ThePolice can be included in any gradle project that is using `jcenter` as a repository (check your main `build.gradle` file) by adding 
`com.safetymarcus.thepolice:processing:1.0.4` to the module `build.gradle` file.

That's it! It's that simple.

#Jar
The latest <a href="https://bintray.com/artifact/download/safetymarcus/maven/1.0.7/ThePolice-1.0.7.jar">JAR</a> contains some improvements over the 1.0.4 version on gradle (it is version 1.0.7). Feel free to use it directly while the gradle version is being set up. This will require that you add `compile 'org.apache.commons:commons-lang3:3.4'` to the `build.gradle` file for your module as it is currently a dependency for the Jar.
