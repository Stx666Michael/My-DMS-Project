# My DMS Project - Breakout
**Tianxiang Song 20217424**

## Preparation & Documentation
### To Compile
- Run `BreakoutApp.main()` in IntelliJ
- Run `mvn clean javafx:run` in terminal

(Choose either)

### Javadoc Path
- `/project/Javadocs`

## Key Changes
### Maintenance
- Apply **MVC pattern** to make development easier and more flexible. This includes:
  - **Splitting up classes** for single responsibility:
    - The original class `Wall` is split into `GameController` and `Wall`
      - `GameController` implements methods which are _called periodically_ in a timeline.
      - `Wall` retains original methods that are used to _reset and initialize game objects_.
    - The original class `GameBoard` is split into `GameController`, `GameRenderer` and `SettingController`
      - `GameController` implements methods that _handles key/mouse event_.
      - `GameRenderer` implements methods that _renders game objects and text_ on screen.
      - `SettingController` implements methods in _Pause Menu_.
    - The original class `Brick` is split into `Brick` and `Crack`.
  - **Organizing classes** into four packages:
    - **main**: package contains one class of `main()` method.
    - **model**: package contains 10 classes mainly used for _storing/accessing data_ of multiple game elements.
    - **controller**: package contains 4 classes mainly used for _handling key/mouse event_ in different scenes.
    - **view**: package contains one class mainly used for _rendering objects_ in game scene.
- Apply **Mediator pattern** to reduce coupling. This includes:
  - Use `Scenes` as mediator for communication between four controllers.
  - Use `Wall` as mediator for communication between GameController and GameRenderer.
- **Improve encapsulation** by making all member variables **private** and accessed through **accessor methods**.
- **Delete** unused images and classes: 
  - `DebugConsole` and `DebugPanel` (Implemented by `SettingController`).
  - `GameFrame` (Implemented by `GameController`).
  - `BrickX` (Implemented by `Brick`).
  - `Ball1` (Implemented by `BonusBall`).
- Add three **JUnit tests** (some use TestFX), one for model class and two for controllers.
- Use **Maven** to easily manage dependencies.
- Use `module-info.java` to package application as a separate Java module.
- Completed **translation** from **Swing to JavaFX** for richer GUI features.
- Use **two separate timelines** for _updating state_ and _rendering_ game objects, which **stabilize animation** 
  (e.g. move speed will not be affected by render speed).
- Apply **Bob's Concise Coding Conventions** for maintenance.
### Extension
- Add **Start Screen, Setting Screen** and **End Screen** using **FXML**, controlled by corresponding **controller classes**.
  - **Start screen** can 
    - Start game.
    - Access **setting screen**.
    - Show **help** information.
  - **Setting screen** can
    - Adjust paddle/ball **speed**.
    - Change three **game themes**.
    - Change paddle **move control** between _keyboard_ and _mouse_.
    - Mute background/effect **audio**.
    - Reset game to first level (activates when game starts).
    - Quit application.
  - **End Screen** can
    - Show score _in total_ and from _current level_ (brick score + left ball score).
    - Show **high score list** by reading `scoreList.txt`.
    - **Enter player name** and save name/score to `scoreList.txt`.
    - Play next level (when win this level).
    - Restart game from first level (when lose or all levels passed).
    - Quit application.
- **Use pictures** for _all game elements_ by inheriting from `Sprite` class.
- Create **three playable game levels**.
- Add **background/effect audio**.
- Add **new game elements** `BonusBall`, `Buff` and `Lightning` (see Javadoc for details).
### Notes
#### Unexpected Problem
- When run `mvn test`, there are _Exceptions_ about TestFX. However, without using `mvn` command, no _Exception_ happens.