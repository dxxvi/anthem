mapt: dillian.khadyn@iillii.org / 1Q...
pluralsight: kamsiyochukwu.jaice@iillii.org / 1Q...

alias aria2c='aria2c --check-certificate=false'

```bash
alias youtube-dl='youtube-dl --no-check-certificate'

G="\[$(tput setaf 39)\]" && H="\[$(tput setaf 131)\]" && R="\[$(tput sgr0)\]" && export PS1="${G}\h${R}:${H}\w${R} $ "

export EDITOR=vim
export PATH=/usr/sbin:/usr/bin:/sbin:/bin:$HOME/sbt/bin:/mnt/c/cygwin/home/DangVu/kafka_2.12-2.3.1/bin
export JAVA_HOME=/usr/lib/jvm/default
```

```javascript
// ==UserScript==
// @name         outlook
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  try to take over the world!
// @author       You
// @match        https://outlook.live.com/mail/0/inbox*
// @grant        GM_addStyle
// ==/UserScript==

(function() {
    'use strict';

    // Steal the idea of https://addons.mozilla.org/en-US/firefox/addon/email-ad-remover/ (download the .xpi then unzip it)
    function removeAdPanel() {
        const navigationPanel = document.querySelector('[aria-label="Navigation pane"]');

        if (window.innerWidth > 989) { // large screens
            const wrapperNode = navigationPanel.parentNode;

            if (wrapperNode.childElementCount === 4) {
                var adPanel = wrapperNode.lastChild;
                if (adPanel) adPanel.style.display = "none";
            }
        }
        else { // small screens
            const wrapperNode = navigationPanel.parentNode.parentNode;

            if (wrapperNode.childElementCount === 2) {
                const adPanel = wrapperNode.lastChild;
                if (adPanel) adPanel.style.display = "none";
            }
        }
    }

    setInterval(removeAdPanel, 1904 * 2);
})();
```

```bash
[[ -f ~/.bashrc ]] && . ~/.bashrc

export JAVA_HOME=$HOME/graalvm-ee-java11-19.3.0.2
export PATH=$HOME/sbt/bin:$HOME/maven/bin:$HOME/node-utils/node_modules/.bin:$PATH:$JAVA_HOME/bin


# If not running interactively, don't do anything
[[ $- != *i* ]] && return

alias ls='ls --color=auto'
alias firefox='nohup firefox-beta -private > /dev/null 2>&1 &'
alias chromium='nohup chromium -incognito > /dev/null 2>&1 &'
alias xrandr-d='xrandr --output eDP1 --mode 1920x1080 --rotate normal --pos 0x0 --output HDMI2 --mode 3840x2160 --rotate normal --pos 1920x0'
alias aria2c='aria2c --bt-max-peers=419 --max-overall-upload-limit=594 --seed-time=0 --bt-request-peer-speed-limit=194K'
alias nmclidwl='nmcli device wifi list'
alias nmclidwc='nmcli device wifi connect'
alias nmclic='nmcli connection'
alias nmclicd='nmcli connection delete'

A="\[$(tput setaf 128)\]"
B="\[$(tput setaf 20)\]"
C="\[$(tput setaf 204)\]"
RESET="\[$(tput sgr0)\]"
PS1="${A}\u${RESET}@${B}\h ${C}\W ${RESET}\$ "
```
