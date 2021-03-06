# BottomBar

[![](https://jitpack.io/v/Nicrob64/BottomBar.svg)](https://jitpack.io/#Nicrob64/BottomBar)

<img src="https://raw.githubusercontent.com/roughike/BottomBar/master/graphics/shy-demo.gif" width="30%" /> <img src="https://raw.githubusercontent.com/roughike/BottomBar/master/graphics/shifting-demo.gif" width="30%" /> <img src="https://raw.githubusercontent.com/roughike/BottomBar/master/graphics/screenshot_tablet.png" width="33%" />

## Changes from the original version of BottomBar

* Support for empty tabs in place of a tab (no listener) but equal tab space. This can be useful if you want to add a floating action button overlapping the tab bar but not as part of the tab bar itself, which was what was used in our app for a while before we axed the floating action button in favour of context based button actions.
    ```xml
        <empty id="@+id/tab_empty"/>
    ```

* Support for buttons instead of tabs
    ```xml
        <button
        id="@+id/tab_nearby"
        icon="@drawable/ic_plus"
        cornerRadius="5"
        iconColor="#ffffff"
        backgroundColor="#29a416"
        />
    ```
    ```java
        bottomBar.setOnButtonClickListener(new OnButtonClickListener() {
			@Override
			public void onButtonClicked(@IdRes int tabId) {
				Toast.makeText(getApplicationContext(), TabMessage.get(tabId, false) + "non-navigation selected", Toast.LENGTH_LONG).show();
				bottomBar.setItems(R.xml.bottombar_tabs_three);
			}
		});
    ```

* Fixes for badges on smaller devices with 5 tabs (performance may vary with more than 5)
* Support for editing tabs on the fly (ButtonBar#setItems clears items prior to updating, see example "Using Button" and click the button)
    ```java
        bottomBar.setItems(R.xml.bottombar_tabs_three);
    ```

* Make icon size static to 32dp
* Support for Active/Inactive icons for selected tabs 
    You can take advantage of this by putting `activeIcon` in your xml file like so : 
    ```xml
        <tab
            id="@+id/tab_friends"
            icon="@drawable/ic_friends"
            activeIcon="@drawable/ic_friends_active"
            title="Friends" />
    ```
    This will change the icon on the tab bar to `ic_friends_active` when it is the currently selected item
* Fixed problems with the onRestoreInstanceState that were in the original

## Mostly original notes from the old version are below, with some information updated

If you need more info about a feature that has been changed or added and it is not here, looking at the sample app is the easiest way. Add an issue here if you would like any changes made or need me to update the readme.

## What?

A custom view component that mimics the new [Material Design Bottom Navigation pattern](https://www.google.com/design/spec/components/bottom-navigation.html).

## API Level

The current minSDK version is **API level 11 (Honeycomb).**

## Gradle steps to add this to your project

Step 1. Add this to your root build.gradle at the end of repositories:
```groovy
allprojects {
    repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Step 2. Add the dependency
```groovy
dependencies {
	...
    implementation 'com.github.Nicrob64:BottomBar:-SNAPSHOT'
    ...
}
}
```
	

## How do I implement this whole thing?

You can add items by **writing a XML resource file**.

### Creating the icons

The icons must be fully opaque, solid black color, 32dp and **with no padding**. (Will be scaled to 32dp regardless of size, so dont make the phone work harder than it needs to) For example, [with Android Asset Studio Generic Icon generator](https://romannurik.github.io/AndroidAssetStudio/icons-generic.html), select "TRIM" and make sure the padding is 0dp. Here's what your icons should look like:

![Sample icons](https://raw.githubusercontent.com/roughike/BottomBar/master/graphics/icons-howto.png)

### Adding items from XML resource

Define your tabs in an XML resource file.

**res/xml/bottombar_tabs.xml:**

```xml
<tabs>
    <tab
        id="@+id/tab_favorites"
        icon="@drawable/ic_favorites"
        title="Favorites" />
    <tab
        id="@+id/tab_nearby"
        icon="@drawable/ic_nearby"
        title="Nearby" />
    <tab
        id="@+id/tab_friends"
        icon="@drawable/ic_friends"
        activeIcon="@drawable/ic_friendsActive"
        title="Friends" />
</tabs>
```

Then, add the BottomBar to your layout and give it a resource id for your tabs xml file.

**layout/activity_main.xml**

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <!-- This could be your fragment container, or something -->
    <FrameLayout
        android:id="@+id/contentContainer"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_above="@+id/bottomBar" />

    <com.roughike.bottombar.BottomBar
        android:id="@+id/bottomBar"
        android:layout_width="match_parent"
        android:layout_height="60dp"
        android:layout_alignParentBottom="true"
        app:bb_tabXmlResource="@xml/bottombar_tabs" />

</RelativeLayout>
```

### Setting up listeners

By default, the tabs don't do anything unless you listen for selection events and do something when the tabs are selected.

**MainActivity.java:**

```java
public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_favorites) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                }
            }
        });
    }
}
```

If you want to listen for reselection events, here's how you do it:

```java
bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
    @Override
    public void onTabReSelected(@IdRes int tabId) {
        if (tabId == R.id.tab_favorites) {
            // The tab with id R.id.tab_favorites was reselected,
            // change your content accordingly.
        }
    }
});
```

### Those color changing tabs look dope. Howdoidodat?

Just add ```barColorWhenSelected``` to each tab. When that tab is selected, the whole BottomBar background color is changed with a nice animation.

**res/xml/bottombar_tabs.xml**

```xml
<tabs>
    <tab
        id="@+id/tab_favorites"
        icon="@drawable/ic_favorites"
        title="Favorites"
        barColorWhenSelected="#5D4037" />
    <!-- You can use @color resources too! -->
</tabs>
```

### How do I draw it under the navbar?

First, define a style that is a child of your main application theme:

**res/values-v21/styles.xml**

```xml
<style name="AppTheme.TransNav" parent="AppTheme">
    <item name="android:navigationBarColor">@android:color/transparent</item>
    <item name="android:windowTranslucentNavigation">true</item>
    <item name="android:windowDrawsSystemBarBackgrounds">true</item>
</style>
```

You'll also have to **make a stub version of the same theme** to avoid crashes in previous API levels than Lollipop:

**res/values/styles.xml**

```xml
<style name="AppTheme.TransNav" parent="AppTheme" />
```

Also include the same stub in your ```values-land-v21.xml``` to avoid transparent navbar and weird behavior on landscape.

**res/values-land-v21.xml:**

```xml
<style name="AppTheme.TransNav" parent="AppTheme" />
```

Apply the theme in ```AndroidManifest.xml``` for your Activity.

**AndroidManifest.xml:**

```xml
<activity android:name=".MyAwesomeActivity" android:theme="@style/AppTheme.TransNav" />
```

Finally, set ```bb_behavior``` to include the ```underNavbar``` flag and you're good to go!

**activity_my_awesome.xml:**

```xml
<com.roughike.bottombar.BottomBar
    android:id="@+id/bottomBar"
    android:layout_width="match_parent"
    android:layout_height="56dp"
    android:layout_alignParentBottom="true"
    app:bb_tabXmlResource="@xml/my_awesome_bottombar_tabs"
    app:bb_behavior="underNavbar" />
```

### What about Tablets?

Specify a different layout for your activity in ```res/layout-sw600dp``` folder and set ```bb_tabletMode``` to true.

**res/layout-sw600dp/activity_main.xml:**

```xml
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.roughike.bottombar.BottomBar
        android:id="@+id/bottomBar"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_alignParentLeft="true"
        app:bb_tabXmlResource="@xml/bottombar_tabs_three"
        app:bb_tabletMode="true" />

    <!-- This could be your fragment container, or something -->
    <FrameLayout
        android:id="@+id/contentContainer"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_toRightOf="@+id/bottomBar" />

</RelativeLayout>
```

### How do I hide it automatically on scroll?

Easy-peasy!

**activity_main.xml:**

```xml
<android.support.design.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <android.support.v4.widget.NestedScrollView
        android:id="@+id/myScrollingContent"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <!-- Your loooooong scrolling content here. -->

    </android.support.v4.widget.NestedScrollView>

    <com.roughike.bottombar.BottomBar
        android:id="@+id/bottomBar"
        android:layout_width="match_parent"
        android:layout_height="60dp"
        android:layout_gravity="bottom"
        app:bb_tabXmlResource="@xml/bottombar_tabs_three"
        app:bb_behavior="shy"/>

</android.support.design.widget.CoordinatorLayout>
```

### Badges

You can easily add badges for showing an unread message count or new items / whatever you like.

```java
BottomBarComponent nearby = bottomBar.getTabWithId(R.id.tab_nearby);
nearby.setBadgeCount(5);

// Remove the badge when you're done with it.
nearby.removeBadge();

//alternatively putting (x < 1) will clear the badge
nearby.setBadgeCount(0);
```

## All customization options

### For the BottomBar

```xml
<com.roughike.bottombar.BottomBar
    android:id="@+id/bottomBar"
    android:layout_width="match_parent"
    android:layout_height="60dp"
    android:layout_alignParentBottom="true"
    app:bb_tabXmlResource="@xml/bottombar_tabs_three"
    app:bb_tabletMode="true"
    app:bb_behavior="shifting|shy|underNavbar"
    app:bb_inActiveTabAlpha="0.6"
    app:bb_activeTabAlpha="1"
    app:bb_inActiveTabColor="#222222"
    app:bb_activeTabColor="@color/colorPrimary"
    app:bb_titleTextAppearance="@style/MyTextAppearance"
    app:bb_titleTypeFace="fonts/MySuperDuperFont.ttf"
    app:bb_showShadow="true" />
```

<dl>
    <dt>bb_tabXmlResource</dt>
    <dd>the XML Resource id for your tabs, that reside in <code>values/xml/</code></dd>
    <dt>bb_tabletMode</dt>
    <dd>if you want the BottomBar to behave differently for tablets. <u>There's an example of this in the sample project!</u></dd>
    <dt>bb_behavior</dt>
    <dd><code>shifting</code>: the selected tab is wider than the rest. <code>shy</code>: put the BottomBar inside a CoordinatorLayout and it'll automatically hide on scroll! <code>underNavbar</code>: draw the BottomBar under the navBar!</dd>
    <dt>bb_inActiveTabAlpha</dt>
    <dd>the alpha value for inactive tabs, that's used in the tab icons and titles.</dd>
    <dt>bb_activeTabAlpha</dt>
    <dd>the alpha value for active tabs, that's used in the tab icons and titles.</dd>
    <dt>bb_inActiveTabColor</dt>
    <dd>the color for inactive tabs, that's used in the tab icons and titles.</dd>
    <dt>bb_activeTabColor</dt>
    <dd>the color for active tabs, that's used in the tab icons and titles.</dd>
    <dt>bb_badgeBackgroundColor</dt>
    <dd>the background color for any Badges in this BottomBar.</dd>
    <dt>bb_titleTextAppearance</dt>
    <dd>custom textAppearance for the titles</dd>
    <dt>bb_titleTypeFace</dt>
    <dd>path for your custom font file, such as <code>fonts/MySuperDuperFont.ttf</code>. In that case your font path would look like <code>src/main/assets/fonts/MySuperDuperFont.ttf</code>, but you only need to provide <code>fonts/MySuperDuperFont.ttf</code>, as the asset folder will be auto-filled for you.</dd>
    <dt>bb_showShadow</dt>
    <dd>controls whether the shadow is shown or hidden, defaults to true.</dd>
</dl>

### For the tabs

```xml
<tab
    id="@+id/tab_recents"
    title="Recents"
    icon="@drawable/empty_icon"
    activeIcon="@drawable/empty_icon_on"
    inActiveColor="#00FF00"
    activeColor="#FF0000"
    barColorWhenSelected="#FF0000"
    badgeBackgroundColor="#FF0000" />
```

<dl>
    <dt>inActiveColor</dt>
    <dd>the color for inactive tabs, that's used in the tab icons and titles.</dd>
    <dt>activeColor</dt>
    <dd>the color for active tabs, that's used in the tab icons and titles.</dd>
    <dt>barColorWhenSelected</dt>
    <dd>the color that the whole BottomBar should be when selected this tab.</dd>
    <dt>badgeBackgroundColor</dt>
    <dd>the background color for any Badges in this tab.</dd>
    <dt>activeIcon</dt>
    <dd>The icon that is shown when the tab is selected</dd>
    <dt></dt>
    <dd></dd>
</dl>


## License

```
BottomBar library for Android
Copyright (c) 2016 Iiro Krankka (http://github.com/roughike).

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
