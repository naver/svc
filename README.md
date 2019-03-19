# SVC
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Download](https://api.bintray.com/packages/bansooknam/SVC/svc/images/download.svg)](https://bintray.com/bansooknam/SVC/svc/_latestVersion)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16) <br>

:rocket: **Easy** and **intuitive** pattern **library** for Android by Naver.

<img src="./doc/img/SVC_LOGO_256.png" align="right" width="30%">


## Why SVC?

MVP and MVVM use Fragment or Activity as "VIew"

So when we write code inside the Fragment or Activity, codes are getting mixed with
"View" code and "Screen Code" such as `onCreate,onCreateView, onViewCreated` or `onSaveInstanceState, onRestoreInstanceState `.`onActivityResult` etc.

It makes hard to see each "View Logic" and "Screen Logic"

And the most important thing is that "Business Logic" can be included in the "View"(Fragment, Activity) easily.

It's because Activity, Fragments are actually "Control Tower" (control lifecycle and receives view events)

SVC gives the way how to divide Screen, View and Business Logic.


For more read check article below

1. 4 reasons why MVP is not good enough
   [https://medium.com/@bansooknam/intro-svc-the-better-pattern-against-mvp-138e6e790bbc](https://medium.com/@bansooknam/intro-svc-the-better-pattern-against-mvp-138e6e790bbc)

2. Detail concept of SVC
   [https://medium.com/@bansooknam/svc-the-better-pattern-against-mvp-66e6d342a23f](https://medium.com/@bansooknam/svc-the-better-pattern-against-mvp-66e6d342a23f)
   
**Ps.** It can be used with ViewModel as Well.
you can check **Examples** here below.<br/>
https://github.com/BansookNam/android-architecture<br/>
https://github.com/BansookNam/svc-lotto

## How to implement
1. For activity and fragment for the screen, should inherit the `SVC_{name of screen class}` class.
2. Add screen annotations `SvcActivity`, `SvcFragment`, `SvcDialogFragment`.
3. And declare `RequireControlTower`, `RequireViews` annotation above the class.
4. Build you project, then `SVC_XXX` class will be generated automatically by svc processor.

### 1. Activity
```kotlin
@SvcActivity
@RequireViews(MainViews::class)
@RequireControlTower(MainControlTower::class) // magic things do happening
class MainActivity : SVC_MainActivity() { // SVC_MainActivity will be generated after build project.
  //extend SVC_{name of class}
}

```

### 2. Fragment
```kotlin
@SvcFragment
@RequireViews(StatisticViews::class)
@RequireControlTower(StatisticControlTower::class) // magic things do happening
class StatisticFragment : SVC_StatisticFragment() { // SVC_StatisticFragment will be generated after build project.
   //extend SVC_{name of class}
}
```

### 3. DialogFragment
```kotlin
@SvcDialogFragment
@RequireViews(SampleActionViews::class)
@RequireControlTower(SampleActionControlTower::class)
@RequireListener(SampleActionDialogListener::class) //listener from another screen.
class SampleActionDialog : SVC_SampleActionDialog() { // SVC_SampleActionDialog will be generated after build project.
    //extend SVC_{name of class}
}
```

## Examples of ControlTower
1. For controlTower, should inherit the `SVC_{name of screen class}` class.
2. Declare annotations `ControlTower`.
3. Declare `RequireScreen`, `RequireViews` annotation above the class.
4. Build you project, then `SVC_YourControlTower` will be generated automatically by svc processor.

### 1. ControlTower

```kotlin
@ControlTower
@RequireViews(StatisticViews::class)
@RequireScreen(StatisticFragment::class) //screen which this controlTower will be used.
class StatisticControlTower : SVC_StatisticControlTower(), StatisticViewsAction { 
    //extend SVC_{name of class}
    //implement ViewsAction which 'Views' is needed.
}

```

### 2. ControlTower (Abstract screen use)

```kotlin
@ControlTower
@RequireViews(CommonViews::class)
@RequireScreen(CommonScreen::class) //abstract screen is available to declare. (CommonScreen is interface)
class CommonControlTower : SVC_CommonControlTower(), CommonViewsAction {
    //extend SVC_{name of class}
    //implement ViewsAction which 'Views' is needed.
}

```


You can really **divide** "Views" from Activity and Fragment.

so you can see logic about real Fragment and Activity very well like below. ([source link](https://github.com/BansookNam/android-architecture/blob/todo-svc-kotlin/todoapp/app/src/main/java/com/example/android/architecture/blueprints/todoapp/screen/taskdetail/TaskDetailActivity.kt))

```kotlin
@SvcActivity
@RequireViews(TaskDetailViews::class)
@RequireControlTower(TaskDetailCT::class)
class TaskDetailActivity : SVC_TaskDetailActivity() {

    var taskId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        /**
         * we should set taskId before super onCreate
         * because createControlTower will be called on super.onCreate()
         * and taskId is non null String type in constructor of TaskDetailCT
         */
        taskId = intent.getStringExtra(EXTRA_TASK_ID)

        super.onCreate(savedInstanceState)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val deletePressed = item.itemId == R.id.menu_delete
        if (deletePressed) ct.deleteTask()
        return deletePressed
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.taskdetail_fragment_menu, menu)
        return true
    }

    fun startEditTastActivity(taskId: String) {
        val intent = Intent(this, AddEditTaskActivity::class.java)
        intent.putExtra(AddEditTaskActivity.ARGUMENT_EDIT_TASK_ID, taskId)
        startActivityForResult(intent, REQUEST_EDIT_TASK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_EDIT_TASK) {
            // If the task was edited successfully, go back to the list.
            if (resultCode == Activity.RESULT_OK) {
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    fun finishAfterDelete() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    companion object {
        const val EXTRA_TASK_ID = "TASK_ID"
        const val REQUEST_EDIT_TASK = 1
    }

}
```



# Definition of “SVC”

Each alphabet stands for.

S — Screen

V — Views

C — Control Tower



+In addition

ViewsAction — Contains user interaction method which "Views" can produce. and Control Tower should know (such as click, swipe, drag..)

# Diagram

## 1. SVC

![diagram](./doc/img/diagram.png)

1. Each "Screen" has "Views" and "ControlTower".
2. "Views" don't know "ControlTower" directly. It knows "ControlTower" as "ViewsAction"
3. "ControlTower" knows "Views"'s public methods and fields.
4. Each "Views" and "ControlTower" has "Screen"



## 2. MVP, MVVM

![diagram](./doc/img/MVP.png)

![diagram](./doc/img/MVVM.png)

When we use MVP or MVVM, "View"(which is implemented on Activity, Fragment) can easily get Bigger with lots of responsibility. It has 3 main responsibility.

1. Screen logics
2. View logics
3. Control tower logics (access directly to Mediator "Presenter" or "ViewModel")



And both MVP, MVVM can call Business logics of Mediator directly.



## 3. SVC with Model

### 3-1. SVC-M (similar with MVP)

![diagram](./doc/img/SVC_M.png)

As same as MVP, Mediator("ControlTower") comunicated with Model.

It's exactly same.


### 3-2. SVC-VM-M type 1
![diagram](./doc/img/SVC_VM_M_type1.png)

We can use ViewModel in ControlTower to take advantage of "Auto Lifecycle Management".
We use `ControlTower` as Mediator and `ViewModel` as data holder.
In this type, `ControlTower` has `Repositories` and manage the datas

### 3-3. SVC-VM-M type 2
![diagram](./doc/img/SVC_VM_M_type2.png)

Similar with type1, however in this architecture `ViewModel` has `Repositories` and manage the data.
You can design in type2 in case of `Repository` can independently divided with `ControlTower`.
`ViewModel` will contain data change methods.


## 4. Difference between MVP,MVVM vs SVC

There are 2 big differences.

1. SVC divides "View"'s 3 responsibilities into 3 parts, which is Screen, ControlTower, Views
2. "Views" cannot call "Control Tower" directly. (Because "Views" knows "ControlTower" as "ViewsAction")



With this 2 big differences. We can

1. Prevent "View" from being "God View"
2. We can see well divided logics.
3. When we write "View" logic we don't need to think about business logics.
4. We don't need to make duplicated methods by interfaces (compare to MVP)
5. We don't need to observe commands with parameter (compare to MVVM)
6. We can use same "Views" in "A Activity" and "B Fragment". (reusable. ex-`CommonViews` from sample)
7. We can understand intuitively with clear naming.


# Include on your project

[![Download](https://api.bintray.com/packages/bansooknam/SVC/svc/images/download.svg)](https://bintray.com/bansooknam/SVC/svc/_latestVersion)
### 1. Project Top Build.gradle

Packages are available in `jcenter`

Include below in your top build.gradle file

```groovy
allprojects {
    repositories {
        jcenter() //add this line
    }
}
```

### 2. Application Build.gradle

Include below in your "application" build.gradle file

```groovy
apply plugin: 'kotlin-kapt'


implementation "com.naver.android.svc:svc:1.0.0-beta10"
kapt "com.naver.android.svc:svc-compiler:1.0.0-beta10"
```



### 3. Done! You can use it!



### 4. Svc-template can makes "works" easier

Github Link: https://github.com/naver/svc-template

If you want to create Activity, Fragment, DialogFragment quickly. Try SvcTemplate.
 1) clone `https://github.com/naver/svc-template.git`

 2) run shell script through command line. (Terminal in mac)`./install.sh`

 3) restart Android Studio

 4) right click, and use "SVC" - "SVC ***"
![svcTemplate](./doc/img/svcTemplate.png)

 5) write screen name, author then finish!

![svcTemplate2](./doc/img/svcTemplate2.png)

![svcTemplate3](./doc/img/svcTemplate3.png)

6. **Happy coding!**



# Reuse of Views and ControlTower

"Views" and "ControlTower" can be reused in different Screens.
(It means it has same look or same viewsAction and proccess)

 1) Views
    you can easily reuse "Views" in this pattern

 2) ControlTower
   If you want to reuse you should implement your screen as "Screen"or make "Abstract Screen (implements Screen)" with common methods then refer the "Abstract Screen" on your reused "Views" or "ControlTower".





## License
SVC is licensed under the Apache License, Version 2.0.
See [LICENSE](LICENSE) for full license text.

```
Copyright 2018 NAVER Corp.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```