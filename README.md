jNotifyOSD
==========
A java API to show notifications, child of <a href="https://github.com/NiconDevTeam/NiconNotifyOSD">NiconNotifyOSD 2.0</a>
<br>Icons taken from the <a href="https://github.com/Nitrux/ardis-icon-theme">Ardis (icon theme)</a>

<img src="https://github.com/adbenitez/jNotifyOSD/blob/master/Screenshot.png"></img>

Some changes:
<ul>    
    <li>Added transparency support to notification.</li>
    <li>NO_ICON option to show a notification without icon, useful to save space in long notifications.</li>
    <li>Enable/Disable printing debug information related with the api.</li>
    <li>Added option to choose the default border of the notification (ROUNDED, RAISED, ETCHED, NONE)</li>
    <li>Added option to choose the default position where the notifications will show up (LEFT, RIGHT, CENTER)</li>
    <li>Added new sound for messages others than error, warning or success.</li>
    <li>The amount of notifications that are displayed are now only limited by your screen height.</li>
    <li>Changed the use of a null layout in notifications.</li>
    <li>Fixed errors in the ServerOSD.</li>
    <li>Changed type of statics fields in Notification class for enum types to avoid errors while calling the funcions like 'show' with arguments in the wrong position.</li>
</ul>
I have not fixed the DesktopConfirm class, at the moment it isn't working
as expected.
