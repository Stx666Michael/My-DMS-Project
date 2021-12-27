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
    - Split original `Wall` into
      - `GameController`: for methods _called periodically_.
      - `Wall`: for other methods.
    - Split original `Brick` into
      - `Crack`: showing cracks on brick.
      - `Brick`: for other methods.
    - Split original `GameBoard` into
      - `GameController`: handling _key/mouse event_.
      - `GameRenderer`: rendering _game objects and text_.
      - `SettingController`: for _Pause Menu_.
  - **Organizing classes** into four packages:
    - **main**: contains one class of `main()` method.
    - **model**: contains classes for _storing/accessing data_.
    - **controller**: contains classes for _handling key/mouse event_ in different scenes.
    - **view**: contains one class for _rendering objects_ in game scene.
- Apply **Mediator pattern** to reduce coupling. This includes:
  - Use `Scenes` as mediator between _four controllers_.
  - Use `Wall` as mediator between `GameController` and `GameRenderer`.
- **Improve encapsulation** by applying **accessor methods**.
- **Delete** unused images and classes: 
  - `DebugConsole` and `DebugPanel` (Implemented by `SettingController`).
  - `GameFrame` (Implemented by `GameController`).
  - `BrickX` (Implemented by `Brick`).
  - `Ball1` (Implemented by `BonusBall`).
- Add three **JUnit tests** (some use TestFX), one for model class and two for controllers.
- Use **Maven** to easily manage dependencies.
- Use `module-info.java` to package application as a separate Java module.
- Complete **translation** from **Swing to JavaFX** for richer GUI features.
- **Inherit common methods** from `Sprite` (for all game elements).
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
    - Choose three **game themes**.
    - Set paddle **controlled by** _keyboard_ or _mouse_.
    - Mute background/effect **audio**.
    - Go to **debug mode** (click title).
    - Change **current level** (debug mode).
    - Reset game to first level (activates when game starts).
    - Quit application.
  - **End Screen** can
    - Show score _in total_ and from _current level_ (brick score + left ball score).
    - Show **high score list** by reading `scoreList.txt`.
    - **Enter player name** and save name/score to `scoreList.txt`.
    - Play next level (when win this level).
    - Restart game from first level (when lose or all levels passed).
    - Quit application.
- **Use pictures** for _all game elements_ and _backgrounds_.
- Create **three playable game levels** (the third is fun :D).
- Add **mouse control** for paddle.
- Add **background/effect audio**.
- Add **"friction"** between `Paddle` and `Ball` (paddle can _change horizontal speed_ of ball)
- Add **new game elements** (details in Javadoc)
  - `BonusBall`: may fall **with acceleration** when _a brick breaks_, act as the main `Ball`.
  - `Buff`: may fall **with acceleration** when _a brick breaks_, two kinds:
    - Red: make the paddle _larger_.
    - Blue: make the paddle "carry" `Lightning`.
  - `Lightning`: Can be "carried" by `Paddle` and `Ball`, a "lightning" paddle makes a "lightning" ball (which is more powerful).

## Notes
### Unexpected Problem
- When run `mvn test`, there are _Exceptions_ about TestFX. However, without using `mvn` command to test, no _Exception_ happens.