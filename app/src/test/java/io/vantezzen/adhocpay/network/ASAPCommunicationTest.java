package io.vantezzen.adhocpay.network;

import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

import net.sharksystem.asap.ASAPException;
import net.sharksystem.asap.android.apps.ASAPActivity;
import net.sharksystem.asap.apps.mock.ASAPSessionMock;

import java.time.LocalDateTime;

import io.vantezzen.adhocpay.controllers.ControllerManager;
import io.vantezzen.adhocpay.controllers.ControllerManagerImpl;
import io.vantezzen.adhocpay.manager.Manager;
import io.vantezzen.adhocpay.manager.ManagerMock;
import io.vantezzen.adhocpay.models.transaction.Transaction;
import io.vantezzen.adhocpay.models.transaction.TransactionRepository;
import io.vantezzen.adhocpay.models.user.User;
import io.vantezzen.adhocpay.models.user.UserRepository;

public class ASAPCommunicationTest {
    @Test
    public void testCanRegister() {
        Manager manager = Mockito.mock(Manager.class);
        ControllerManager controllerManager = new ControllerManagerImpl(manager);

        ASAPCommunication communication = new ASAPCommunication(manager, controllerManager);

        // Communication sollte sich als ASAP Listener registriert haben
        Mockito.verify(manager).registerASAPListener(communication);
    }

    @Test
    public void testCanSendMessage() throws ASAPException {
        // Setze benötigte Klassen auf
        Manager manager = Mockito.mock(Manager.class);
        ControllerManager controllerManager = new ControllerManagerImpl(manager);
        ASAPActivity activityMock = Mockito.mock(ASAPActivity.class);
        UserRepository ur = new UserRepository();
        TransactionRepository tr = new TransactionRepository(ur, false);

        // Setze Mock antworten auf
        Mockito.when(manager.getActivity()).thenReturn(activityMock);
        Mockito.when(manager.getAppName()).thenReturn("TEST");
        Mockito.when(manager.getDefaultUri()).thenReturn("URI");

        String shouldSendString = "{\"fromUser\":{\"username\":\"karlos\"},\"toUser\":{\"username\":\"peteros\"},\"amount\":15.99,\"time\":\"1::Dec::2020 00::15::00\",\"LOG_START\":\"Model:Transaction\"}";
        byte[] shouldSend = shouldSendString.getBytes();

        // Sende eine Transaktion über den ASAPCommunicator
        ASAPCommunication communication = new ASAPCommunication(manager, controllerManager);
        Transaction testTransaction = new Transaction("karlos", "peteros", 15.99f, LocalDateTime.of(2020, 12,01,00, 15), ur, tr);
        communication.sendTransaction(testTransaction);

        // Communication sollte eine Nachricht über die Activity gesendet haben

        Mockito.verify(activityMock).sendASAPMessage("TEST", "URI", shouldSend, true);
    }
}