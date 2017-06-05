# jNotifyOSD

A java API to show notifications, based on [NiconNotifyOSD 2.0](https://github.com/NiconDevTeam/NiconNotifyOSD)

![alt jNotifyOSD-Screenshot](https://github.com/adbenitez/jNotifyOSD/blob/master/assets/Screenshot.png)

### **Some features:**
* Transparency support for notifications.
* You can change the color of the icons to suit your needs.
* NONE icon option to show a notification without icon, useful to save space for long notifications.
* Enable/Disable printing debug information related with the api.
* Border option for notifications (ROUNDED, RAISED, ETCHED, NONE)
* You can select the position where the notifications will show up (LEFT, RIGHT, CENTER)
* You can choose the theme for new notifications (DARK, LIGHT, GRAY, DEFAULT), DEFAULT theme will look like your application is look and feel, so it usually integrates better with your application than the others themes.
* The amount of notifications that are displayed at the same time is only limited by user's screen height, not a static amount like NiconNotifyOSD 2.0 does.
* Don't like the default icons and sounds? you can set your own pack at run time.
* All this in less than 500 KB.

**IMPORTANT:** 
* I haven't fixed the DesktopConfirm class, at the moment it isn't working as expected.
* Windows users of this API say that ROUNDED border option is working as NONE border in Windows OS.

# Example Usage

The following code will show a notification with all the default settings: sounds on, DEFAULT theme, ROUNDED border, RIGHT orientation, INFORMATION message, during 10 seconds.

```java
import javax.swing.SwingUtilities;
import adbenitez.notify.Notification;

public class Demo {
    public Demo() {
        // Show default notification:
        Notification.show("Hello World!", "This is just a test.");
    }
    public static void main(String[] args) {
         SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            new Demo();
         }
      });
    }
}
```

# Getting Help

* Add an issue on GitHub
* Write to asieldbenitez at gmail.com
