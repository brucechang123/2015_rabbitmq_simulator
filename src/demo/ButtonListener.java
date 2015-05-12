package demo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class ButtonListener implements ActionListener {
	static Thread thread;
	private static boolean isRunning = true;

	public ButtonListener() {

	}

	public static class btnSendListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String msg = RMQSimulator.jtfSendMessage.getText();
			String strExchange = "demo.EXCHANGE1";
			String strUri = "amqp://admin:admin@wearable.nccu.edu.tw";

			strExchange = (String) RMQSimulator.jcbDestinationName
					.getSelectedItem();
			strUri = (String) RMQSimulator.jcbServerUri.getSelectedItem();
			RMQSimulator.jtaMessageSent.append(msg + "\n");

			try {
				ConnectionFactory factory = new ConnectionFactory();
				try {
					factory.setUri(strUri);
				} catch (KeyManagementException | NoSuchAlgorithmException
						| URISyntaxException e1) { 
				}
				Connection connection = factory.newConnection();
				Channel channel = connection.createChannel();

				channel.exchangeDeclare(strExchange, "fanout");

				channel.basicPublish(strExchange, "", null, msg.getBytes());
				System.out.println(" [x] Sent '" + msg + "' to " + strExchange);

				channel.close();
				connection.close();
			} catch (IOException e1) {
			}
		}
	}

	public static class btnMessageSentClearListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			RMQSimulator.jtaMessageSent.setText(null);
		}
	}

	public static class btnMessageReceivedClearListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			RMQSimulator.jtaMessageReceived.setText(null);
		}
	}

	public static class btnConnectListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			isRunning = true;
			thread = new Thread(
					() -> {
						String strExchange = "demo.EXCHANGE1";
						String strUri = "amqp://admin:admin@wearable.nccu.edu.tw";

						strExchange = (String) RMQSimulator.jcbListenTopic
								.getSelectedItem();
						strUri = (String) RMQSimulator.jcbRMQUri
								.getSelectedItem();
						try {
							ConnectionFactory factory = new ConnectionFactory();
							try {
								factory.setUri(strUri);
							} catch (KeyManagementException
									| NoSuchAlgorithmException
									| URISyntaxException e1) {
							}
							Connection connection = factory.newConnection();
							Channel channel = connection.createChannel();

							channel.exchangeDeclare(strExchange, "fanout");
							String queueName = channel.queueDeclare()
									.getQueue();
							channel.queueBind(queueName, strExchange, "");

							System.out
									.println(" [*] Waiting for messages. To exit press CTRL+C");

							QueueingConsumer consumer = new QueueingConsumer(
									channel);
							channel.basicConsume(queueName, true, consumer);

							while (isRunning) {

								try {
									QueueingConsumer.Delivery delivery = null;
									delivery = consumer.nextDelivery();
									String message = new String(delivery
											.getBody());
									RMQSimulator.vertical.setValue( RMQSimulator.vertical.getMaximum() );

									System.out.println(" [x] Received '"
											+ message + "'");
									RMQSimulator.jtaMessageReceived
											.append(message + "\n");
								} catch (ShutdownSignalException
										| ConsumerCancelledException
										| InterruptedException e3) {
								}

							}
						} catch (IOException e2) {

						}
					});
			thread.start();
			RMQSimulator.btnConnect.setVisible(false);
			RMQSimulator.btnDisconnect.setVisible(true);
			RMQSimulator.jcbRMQUri.setEnabled(false);
			RMQSimulator.jcbListenTopic.setEnabled(false);
		}
	}

	public static class btnDisconnectListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			isRunning = false;
			thread.interrupt();
			RMQSimulator.btnConnect.setVisible(true);
			RMQSimulator.btnDisconnect.setVisible(false);
			RMQSimulator.jcbRMQUri.setEnabled(true);
			RMQSimulator.jcbListenTopic.setEnabled(true);
		}
	}

	public void actionPerformed(ActionEvent e) {

	}
}
