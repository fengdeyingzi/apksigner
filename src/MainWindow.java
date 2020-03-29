
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

public class MainWindow extends JFrame {

	private final JTextField textField_file;
	private final JTextField textfield_keypathField;
	private final JTextField textfield_passwordField;
	private final JTextField textfield_keynameField;
	private final JTextArea label_info;
	private JPanel panel = new JPanel();
	private JFileChooser fileChooser = new JFileChooser();

	public MainWindow() {
		setTitle("apk签名 - 风的影子 制作");
		setBounds(400, 400, 400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		final JLabel label = new JLabel();
		label.setText("文件：");
		panel.add(label);

		textField_file = new JTextField();
		textField_file.setColumns(20);
		panel.add(textField_file);

		final JButton button = new JButton("上传");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				int i = fileChooser.showOpenDialog(getContentPane());// 显示文件选择对话框

				// 判断用户单击的是否为“打开”按钮
				if (i == JFileChooser.APPROVE_OPTION) {

					File selectedFile = fileChooser.getSelectedFile();// 获得选中的文件对象
					textField_file.setText(selectedFile.getPath());// 显示选中文件的名称
				}
			}
		});
		panel.add(button);
		JLabel label_keypathJLabel = new JLabel("key路径");
		textfield_keypathField = new JTextField();
		textfield_keypathField.setColumns(20);
		final JButton button_setkey = new JButton("设置");
		button_setkey.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				int i = fileChooser.showOpenDialog(getContentPane());// 显示文件选择对话框

				// 判断用户单击的是否为“打开”按钮
				if (i == JFileChooser.APPROVE_OPTION) {

					File selectedFile = fileChooser.getSelectedFile();// 获得选中的文件对象
					textfield_keypathField.setText(selectedFile.getPath());// 显示选中文件的名称
				}
			}
		});

		JLabel lable_keynameJLabel = new JLabel("key名字");
		textfield_keynameField = new JTextField();
		textfield_keynameField.setColumns(20);

		JLabel lable_passwordJLabel = new JLabel();
		lable_passwordJLabel.setText("key密码");
		textfield_passwordField = new JTextField();
		textfield_passwordField.setColumns(20);

		JPanel panel_0 = new JPanel();
		panel_0.setPreferredSize(new Dimension(640, 30));
		panel_0.add(label_keypathJLabel);
		panel_0.add(textfield_keypathField);
		panel_0.add(button_setkey);

		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(640, 30));
		panel_1.add(lable_keynameJLabel);
		panel_1.add(textfield_keynameField);

		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new DimensionUIResource(640, 30));
		panel_2.add(lable_passwordJLabel);
		panel_2.add(textfield_passwordField);

		JPanel panel_sign = new JPanel();
		JButton btn_sign = new JButton();
		btn_sign.setText("签名");
		panel_sign.add(btn_sign);

		JPanel panel_info = new JPanel();
		label_info = new JTextArea();
		label_info.setEditable(false);
		label_info.setText("输出信息:");
		label_info.setMaximumSize(new Dimension(640, 480));
		label_info.setPreferredSize(new Dimension(640, 60));

		panel_info.add(label_info);

		panel.add(panel_0);
		panel.add(panel_1);
		panel.add(panel_2);
		panel.add(panel_info);
		panel.add(panel_sign);

		btn_sign.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					MainWindow.this.signApk(textField_file.getText(), textfield_keypathField.getText(),
							textfield_keynameField.getText(), textfield_passwordField.getText());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

//		add(panel, BorderLayout.NORTH);
		setContentPane(panel);

		setVisible(true);
	}

	public void signApk(String path, String key, String appkey, String password)
			throws IOException, InterruptedException {
		// 4----签名 （文件名称中不能包含空格）
		String jdkBinPath = "C:\\Program Files\\Java\\jdk1.6.0_26\\bin";
		String path_new = path.substring(0, path.length() - 4) + "_sign.apk";
		File bin = new File(jdkBinPath);
		File file_out = new File(path_new);
//		   file_out.createNewFile();
		String cmd = "jarsigner -keystore " + key + " -storepass " + password + " -keypass " + password + " -signedjar "
				+ path_new + " " + path + "" + " " + appkey;
		System.out.println("" + cmd);
		label_info.append(cmd + "\r\n");
		Process process = Runtime.getRuntime().exec(cmd, null);
		if (process.waitFor() != 0)
			System.out.println("签名失败。。。");
		else {
			System.out.println("签名成功");
			label_info.append("签名成功\r\n");
		}
		BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = "";
		while ((line = input.readLine()) != null) {
			System.out.println(line);
			label_info.append(line + "\r\n");
		}
		input.close();
		// jarsigner -verbose -keystore /Users/mac/Downloads/xl.keystore -signedjar
		// "/Users/mac/Downloads/app-release_legu.apk"
		// "/Users/mac/Downloads/app-release_legu_sign.apk" appkey

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainWindow test = new MainWindow();

	}

}