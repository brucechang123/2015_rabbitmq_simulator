package demo;

import java.awt.Button;
import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RMQSimulator extends JFrame {
	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	// JLabel declaration
	private JLabel jlbMessageSent;
	private JLabel jlbDestinationName;
	private JLabel jlbServerUri;
	//private JLabel jlbDefaultUri;
	private JLabel jlbMessageReceived;
	private JLabel jlbRMQUri;
	private JLabel jlbListenTopic;

	// Button declaration
	private Button btnMessageSentClear;
	private Button btnSend;
	private Button btnMessageReceivedClear;
	public static Button btnConnect;
	public static Button btnDisconnect;

	// JScrollPane declaration
	private JScrollPane jspMessageSent;
	private JScrollPane jspMessageReceived;

	// JTextArea declaration
	public static JTextArea jtaMessageSent;
	public static JTextArea jtaMessageReceived;

	// JTextField declaration
	public static JTextField jtfSendMessage;

	// JComboBox declaration
	public static JComboBox<String> jcbDestinationName;
	public static JComboBox<String> jcbServerUri;
	public static JComboBox<String> jcbRMQUri;
	public static JComboBox<String> jcbListenTopic;
	
	public static JScrollBar vertical;
	String strDestinationName[] = { "demo.EXCHANGE1", "demo.EXCHANGE2",
			"position", "rotation", "rotation_raw" };
	/*String strServerUri[] = { "amqp://guest:guest@140.119.176.122",
			"amqp://admin:admin@140.119.176.124" };*/
	String strServerUri[] = {"amqp://admin:admin@wearable.nccu.edu.tw" };

	public RMQSimulator() {
		initComponents();
		initEventListeners();
		initJLabel();
		initButton();
		initTextArea();
		initTextField();
		initComboBox();
	}

	private void initComponents() {
		setTitle("RMQSimulator");
		setSize(500, 600);
		setLayout(null);
		setLocation(350, 250);
		setResizable(false);
		setVisible(true);
	}

	private void initEventListeners() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initJLabel() {
		jlbMessageSent = new JLabel("�ǰe�T��");
		jlbMessageSent.setFont(new java.awt.Font("Dialog", 0, 13));
		jlbMessageSent.setBounds(10, 5, 70, 25);
		add(jlbMessageSent);

		jlbDestinationName = new JLabel("Destination Name: ");
		jlbDestinationName.setFont(new java.awt.Font("Dialog", 0, 13));
		jlbDestinationName.setBounds(10, 225, 150, 25);
		add(jlbDestinationName);

		jlbServerUri = new JLabel("Server URL:");
		jlbServerUri.setFont(new java.awt.Font("Dialog", 0, 13));
		jlbServerUri.setBounds(10, 260, 150, 25);
		add(jlbServerUri);

		jlbMessageReceived = new JLabel("�����T��");
		jlbMessageReceived.setFont(new java.awt.Font("Dialog", 0, 13));
		jlbMessageReceived.setBounds(10, 310, 70, 25);
		add(jlbMessageReceived);

		jlbRMQUri = new JLabel("Server URL:");
		jlbRMQUri.setFont(new java.awt.Font("Dialog", 0, 13));
		jlbRMQUri.setBounds(10, 500, 150, 25);
		add(jlbRMQUri);

		jlbListenTopic = new JLabel("Listen Topic:");
		jlbListenTopic.setFont(new java.awt.Font("Dialog", 0, 13));
		jlbListenTopic.setBounds(10, 535, 150, 25);
		add(jlbListenTopic);
	}

	private void initButton() {
		btnMessageSentClear = new Button("Clear");
		btnMessageSentClear.setFont(new java.awt.Font("Dialog", 0, 13));
		btnMessageSentClear.setBounds(420, 5, 65, 25);
		btnMessageSentClear
				.addActionListener(new ButtonListener.btnMessageSentClearListener());
		add(btnMessageSentClear);

		btnSend = new Button("Send");
		btnSend.setFont(new java.awt.Font("Dialog", 0, 13));
		btnSend.setBounds(420, 190, 65, 25);
		btnSend.addActionListener(new ButtonListener.btnSendListener());
		add(btnSend);

		btnMessageReceivedClear = new Button("Clear");
		btnMessageReceivedClear.setFont(new java.awt.Font("Dialog", 0, 13));
		btnMessageReceivedClear.setBounds(420, 310, 65, 25);
		btnMessageReceivedClear
		.addActionListener(new ButtonListener.btnMessageReceivedClearListener());
		add(btnMessageReceivedClear);

		btnConnect = new Button("Connect");
		btnConnect.setFont(new java.awt.Font("Dialog", 0, 13));
		btnConnect.setBounds(410, 500, 75, 25);
		btnConnect.addActionListener(new ButtonListener.btnConnectListener());
		add(btnConnect);
		
		btnDisconnect = new Button("Disconnect");
		btnDisconnect.setFont(new java.awt.Font("Dialog", 0, 13));
		btnDisconnect.setBounds(410, 500, 75, 25);
		btnDisconnect.addActionListener(new ButtonListener.btnDisconnectListener());
		btnDisconnect.setVisible(false);
		add(btnDisconnect);
	}

	private void initTextArea() {
		jtaMessageSent = new JTextArea();
		jtaMessageSent.setEditable(false);
		jtaMessageSent.setFocusable(false);
		jtaMessageSent.setLineWrap(true);
		jtaMessageSent.setWrapStyleWord(true);

		jspMessageSent = new JScrollPane(jtaMessageSent);
		jspMessageSent.setBounds(10, 35, 475, 150);
		add(jspMessageSent);

		jtaMessageReceived = new JTextArea();
		jtaMessageReceived.setEditable(false);
		jtaMessageReceived.setFocusable(false);
		jtaMessageReceived.setLineWrap(true);
		jtaMessageReceived.setWrapStyleWord(true);

		jspMessageReceived = new JScrollPane(jtaMessageReceived);
		jspMessageReceived.setBounds(10, 340, 475, 150);
		vertical = jspMessageReceived.getVerticalScrollBar();
		add(jspMessageReceived);
	}

	private void initTextField() {
		jtfSendMessage = new JTextField();
		jtfSendMessage.setBounds(10, 190, 400, 25);
		add(jtfSendMessage);
	}

	private void initComboBox() {
		jcbDestinationName = new JComboBox<>(strDestinationName);
		jcbDestinationName.setBounds(130, 225, 355, 25);
		jcbDestinationName.setEditable(true);
		jcbDestinationName.setFont(new java.awt.Font("Dialog", 0, 13));
		jcbDestinationName.setBackground(Color.white);
		add(jcbDestinationName);

		jcbServerUri = new JComboBox<>(strServerUri);
		jcbServerUri.setBounds(130, 260, 355, 25);
		//jcbServerUri.setEditable(true);
		jcbServerUri.setFont(new java.awt.Font("Dialog", 0, 13));
		jcbServerUri.setBackground(Color.white);
		add(jcbServerUri);

		jcbRMQUri = new JComboBox<>(strServerUri);
		jcbRMQUri.setBounds(90, 500, 310, 25);
		//jcbRMQUri.setEditable(true);
		jcbRMQUri.setFont(new java.awt.Font("Dialog", 0, 13));
		jcbRMQUri.setBackground(Color.white);
		add(jcbRMQUri);
		
		jcbListenTopic = new JComboBox<>(strDestinationName);
		jcbListenTopic.setBounds(90, 535, 310, 25);
		jcbListenTopic.setEditable(true);
		jcbListenTopic.setFont(new java.awt.Font("Dialog", 0, 13));
		jcbListenTopic.setBackground(Color.white);
		add(jcbListenTopic);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> {
			new RMQSimulator().setVisible(true);
		});
	}
}
