package io.vantezzen.adhocpay.network;

import org.junit.Test;
import org.mockito.Mockito;

import net.sharksystem.asap.ASAPException;
import net.sharksystem.asap.android.apps.ASAPActivity;

import java.time.LocalDateTime;

import io.vantezzen.adhocpay.AdHocPayApplication;
import io.vantezzen.adhocpay.controllers.ControllerManager;
import io.vantezzen.adhocpay.controllers.ControllerManagerImpl;
import io.vantezzen.adhocpay.manager.Manager;
import io.vantezzen.adhocpay.models.transaction.Transaction;
import io.vantezzen.adhocpay.models.transaction.TransactionRepository;
import io.vantezzen.adhocpay.models.user.UserRepository;
import io.vantezzen.adhocpay.network.asap.ASAPApp;

public class ASAPCommunicationTest {
    @Test
    public void testCanRegister() {
        Manager manager = Mockito.mock(Manager.class);
        ControllerManager controllerManager = new ControllerManagerImpl(manager);

        AdHocPayApplication.useTestApplication();
        ASAPCommunication communication = new ASAPCommunication(manager, controllerManager);

        // Communication sollte sich als ASAP Listener registriert haben
        Mockito.verify(ASAPApp.getInstance()).addASAPMessageReceivedListener(manager.getAppName(), communication);
    }

    @Test
    public void testCanSendMessage() throws ASAPException {
        // Setze benötigte Klassen auf
        AdHocPayApplication.useTestApplication();
        Manager manager = Mockito.mock(Manager.class);
        ControllerManager controllerManager = new ControllerManagerImpl(manager);
        ASAPActivity activityMock = Mockito.mock(ASAPActivity.class);
        UserRepository ur = new UserRepository();
        TransactionRepository tr = new TransactionRepository(ur, false);

        // Setze Mock antworten auf
        Mockito.when(manager.getActivity()).thenReturn(activityMock);
        Mockito.when(manager.getAppName()).thenReturn("TEST");
        Mockito.when(manager.getDefaultUri()).thenReturn("URI");

        byte[][] shouldSend = new byte[][]{
                "__START__".getBytes(),
                "{\"fromUser\":{\"username\":\"karlos\"},\"toUser\":{\"username\":\"peteros\"},\"amount\":15.99".getBytes(),
                ",\"time\":\"2020-12-1 0:15:0\",\"id\":999}".getBytes(),
                "__END__".getBytes()
        };

        // Sende eine Transaktion über den ASAPCommunicator
        ASAPCommunication communication = new ASAPCommunication(manager, controllerManager);
        Transaction testTransaction = new Transaction("karlos", "peteros", 15.99f, LocalDateTime.of(2020, 12,1,0, 15), ur, tr);
        testTransaction.id = 999;
        communication.sendTransaction(testTransaction);

        // Communication sollte Nachrichten über die Activity gesendet haben
        for(byte[] should : shouldSend) {
            Mockito.verify(activityMock).sendASAPMessage("TEST", "URI", should, true);
        }
    }
}