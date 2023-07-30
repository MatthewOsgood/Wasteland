package com.mygdx.game.View.dialogue;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.enums.ConversationPath;
import com.mygdx.game.enums.SkinPaths;
import com.mygdx.game.enums.TexturePath;

import java.util.Iterator;

public class DialogueOverlay {
    private final SteampunkGame game;
    public final Stage stage;
    private final OrthographicCamera camera;

    private final Table table;
    private Iterator<DialogueLine> conversation;
    /**
     * always drawn on the right of the screen
     */
    private final Image speaker;
    /**
     * always drawn on the left of the screen
     */
    private final Image player;
    private final Label currentText;
    private boolean isVisible;
    private final BitmapFont font;

    public DialogueOverlay(SteampunkGame game) {
        this.game = game;
        this.camera = new OrthographicCamera(SteampunkGame.VIEW_WIDTH, SteampunkGame.VIEW_HEIGHT);
        ScreenViewport viewport = new ScreenViewport(this.camera);
        this.stage = new Stage(viewport, game.batch);
        this.table = new Table();
        this.table.setFillParent(true);
        this.table.bottom();
        this.player = new Image(this.game.assetManager.get(TexturePath.PLAYER.getPath(), Texture.class));
        this.isVisible = false;
        this.font = game.font;

        this.currentText = new Label("", game.assetManager.<Skin>get(SkinPaths.TEST.getPath()));
        this.currentText.setAlignment(Align.topLeft);
        this.currentText.setWrap(true);
        this.speaker = new Image();

        this.table.add(this.player).align(Align.left).maxWidth(this.camera.viewportWidth / 3f).padRight(this.camera.viewportWidth / 6f);
        this.table.add(this.speaker).align(Align.right).maxWidth(this.camera.viewportWidth / 3f).padLeft(this.camera.viewportWidth / 6f);
        this.table.row();
        this.table.add(this.currentText).colspan(2).prefHeight(this.camera.viewportHeight / 3f).prefWidth(this.camera.viewportWidth / 2f).align(Align.center).padBottom(this.camera.viewportHeight / 12f);
        this.stage.addActor(this.table);
        this.stage.setDebugAll(true);
    }

    public void setConversation(ConversationPath conversationPath) {
        this.conversation = new Json().fromJson(Conversation.class, new FileHandle(conversationPath.Path())).iterator();
    }

    public void draw() {
        if (this.isVisible) this.stage.draw();
    }

    /**
     * advances this overlay to the next line of dialogue
     *
     * @return true if the conversation is complete and there is no next line
     */
    public boolean update() {
        if (this.conversation.hasNext()) {
            DialogueLine nextLine = this.conversation.next();
            this.currentText.setText(nextLine.getDialogue());
            if (nextLine.getSpeaker().isPlayer()) {
                this.player.setVisible(true);
                this.speaker.setVisible(false);
            } else {
                this.speaker.setDrawable(new SpriteDrawable(new Sprite(this.game.assetManager.get(nextLine.getSpeaker().AssetPath().getPath(), Texture.class))));
                this.speaker.setVisible(true);
                this.player.setVisible(false);
            }
            return false;
        } else {
            return true;
        }
    }

    public void setVisibility(boolean visible) {
        this.isVisible = visible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void dispose() {
        this.stage.dispose();
    }
}
