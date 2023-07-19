package com.mygdx.game.View.Dialogue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.ConversationPath;
import com.mygdx.game.SteampunkGame;
import com.mygdx.game.TexturePath;

public class DialogueOverlay {
    private final Stage stage;
    private final OrthographicCamera camera;

    private final Table table;
    private final Conversation conversation;
    /**
     * always drawn on the right of the screen
     */
    private Image speaker;
    /**
     * always drawn on the left of the screen
     */
    private Image player;
    private Label currentText;

    DialogueOverlay(SteampunkGame game, ConversationPath conversationPath) {
        FitViewport viewport = new FitViewport(SteampunkGame.VIEW_WIDTH, SteampunkGame.VIEW_HEIGHT);
        this.stage = new Stage(viewport, game.batch);
        this.camera = new OrthographicCamera(SteampunkGame.VIEW_WIDTH, SteampunkGame.VIEW_HEIGHT);
        this.conversation = game.json.fromJson(Conversation.class, new FileHandle(conversationPath.Path()));
        this.table = new Table();
        this.table.setFillParent(true);
        this.table.bottom();
        this.player = new Image(new Texture(Gdx.files.internal(TexturePath.TEST_CHARACTER.getPath())));
        DialogueLine firstLine = this.conversation.next();
        this.speaker = new Image(new Texture(Gdx.files.internal(firstLine.getSpeaker().AssetPath().getPath())));
        this.currentText = new Label(firstLine.getDialogue(), new Label.LabelStyle());
    }

    public void draw(SpriteBatch batch, BitmapFont font) {
        this.stage.draw();
    }

    /**
     * advances this overlay to the next line of dialogue
     *
     * @return true if this was updated, false if there was no next line to update to
     */
    public boolean update() {
        if (this.conversation.hasNext()) {
            DialogueLine nextLine = this.conversation.next();
            this.currentText.setText(nextLine.getDialogue());
            if (nextLine.getSpeaker().isPlayer()) {
                this.player.setVisible(true);
                this.speaker.setVisible(false);
            } else {
                this.speaker.setDrawable(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal(nextLine.getSpeaker().AssetPath().getPath())))));
                this.speaker.setVisible(true);
                this.player.setVisible(false);
            }
            return true;
        } else {
            return false;
        }
    }


    public void dispose() {
        this.stage.dispose();
    }
}
