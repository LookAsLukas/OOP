package ru.nsu.nmashkin.task231;

/**
 * Bot logic interface.
 */
public interface BotLogic {
    /**
     * Return direction to move next.
     *
     * @return .
     */
    Direction nextMove();

    /**
     * Sets a bot related with logic.
     */
    void setBot(Snake bot);
}
