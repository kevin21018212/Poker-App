import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/poker/{tableId}/{username}", configurator = SpringConfigurator.class)
@Component
public class PokerGameServer {

    // Store active poker games by table ID
    private static Map<String, PokerGame> pokerGameMap = new ConcurrentHashMap<>();

    // Called when a new WebSocket connection is established
    @OnOpen
    public void onOpen(Session session, @PathParam("tableId") String tableId, @PathParam("username") String username) throws IOException {
        // retrive or create a game
        PokerGame pokerGame = pokerGameMap.computeIfAbsent(tableId, PokerGame::new);

        // add player to poker game
        pokerGame.addPlayer(username, session);

        // Send game state to client
        String gameState = pokerGame.getGameState(username);
        session.getBasicRemote().sendText(gameState);
    }

    // when message is recived from client
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        String username = getUsernameBySession(session);
        if (username == null) {
            // handle error or unauthorized action
            return;
        }

        PokerGame pokerGame = getGameBySession(session);
        if (pokerGame != null) {
            // handle player actions
            pokerGame.handlePlayerAction(username, message);

            if (pokerGame.isHandComplete()) {
                pokerGame.determineWinnerAndResetGame();
            }

            // send new game state to all players
            broadcastGameState(pokerGame, username);
        }
    }

    //when connection is closed
    @OnClose
    public void onClose(Session session) throws IOException {
        String username = getUsernameBySession(session);
        if (username != null) {
            PokerGame pokerGame = getGameBySession(session);
            if (pokerGame != null) {
                //player disconnection
                pokerGame.handlePlayerDisconnect(username);

                //send update game state to all players
                broadcastGameState(pokerGame, username);
            }
        }
    }

    // Retrieve the game from session
    private PokerGame getGameBySession(Session session) {
        String tableId = session.getPathParameters().get("tableId");
        return pokerGameMap.get(tableId);
    }

    // Retrieve the username associated with a session
    private String getUsernameBySession(Session session) {
        return session.getPathParameters().get("username");
    }

    //send the updated game state to all players except the excluded player
    private void broadcastGameState(PokerGame pokerGame, String excludedPlayer) throws IOException {
        for (Session playerSession : pokerGame.getActivePlayerSessions()) {
            if (!playerSession.isOpen()) {
                continue;
            }

            String gameState = pokerGame.getGameState(getUsernameBySession(playerSession));
            playerSession.getBasicRemote().sendText(gameState);
        }
    }



     private void handleGameLogic(Session session) {
         // Implement game logic
     }

     private void handlePlayerAction(Session session, String message) {
         //player action logic
     }

     private void determineWinnerAndResetGame() {
         //determine the winner and reset the game
     }

     private void handlePlayerDisconnect(String username) {
         //player disconnect logic
     }
}
