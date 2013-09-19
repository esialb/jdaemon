package org.rkutil.jdaemon;

import java.io.FileInputStream;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.PosixParser;

import static org.rkutil.jdaemon.DaemonOptions.*;

public class Daemon {
	public static void main(String[] s) throws Exception {
		List<String> vmArgs = ManagementFactory.getRuntimeMXBean().getInputArguments();
		List<String> daemonArgs = new ArrayList<String>();
		List<String> toolArgs = new ArrayList<String>(Arrays.asList(s));
		
		while(toolArgs.size() > 0) {
			String arg = toolArgs.remove(0);
			if("--".equals(arg))
				break;
			daemonArgs.add(arg);
		}
		
		CommandLine cli;
		
		try {
			cli = new PosixParser().parse(new DaemonOptions(), daemonArgs.toArray(new String[0]));
			if(cli.hasOption(HELP) || !cli.hasOption(MAIN))
				throw new Exception("show help");
		} catch(Exception ex) {
			help();
			return;
		}
		
		Class<?> toolClass;
		if(cli.hasOption(MAIN))
			toolClass = Class.forName(cli.getOptionValue(MAIN));
		else if(cli.hasOption(METAINF)) {
			Properties ini = new Properties();
			ini.load(Daemon.class.getClassLoader().getResourceAsStream("META-INF/jdaemon.ini"));
			toolClass = Class.forName(ini.getProperty(MAIN));
		} else {
			System.out.println("Must specify either --" + MAIN + " or --" + METAINF);
			return;
		}

		if(!cli.hasOption(FOREGROUND)) {
			fork(vmArgs, daemonArgs, toolArgs);
			return;
		}
		
		System.setOut(new PrintStream(new DiscardingOutputStream()));
		System.setErr(new PrintStream(new DiscardingOutputStream()));
		System.setIn(new EmptyInputStream());
		
		if(cli.hasOption(STDIN))
			System.setIn(new FileInputStream(cli.getOptionValue(STDIN)));
		if(cli.hasOption(STDOUT))
			System.setOut(new PrintStream(cli.getOptionValue(STDOUT)));
		if(cli.hasOption(REDIRECT))
			System.setErr(System.out);
		else if(cli.hasOption(STDERR))
			System.setErr(new PrintStream(cli.getOptionValue(STDERR)));
		
		Method toolMain = toolClass.getMethod("main", String[].class);
		toolMain.invoke(null, new Object[] { toolArgs.toArray(new String[0]) });
	}
	
	private static Process fork(List<String> vmArgs, List<String> daemonArgs, List<String> appArgs) throws Exception {
		daemonArgs = new ArrayList<String>(daemonArgs);
		daemonArgs.add(0, "--" + FOREGROUND);
		
		List<String> cmd = new ArrayList<String>();
		cmd.add("java");
		cmd.addAll(vmArgs);
		cmd.addAll(daemonArgs);
		cmd.add("--");
		cmd.addAll(appArgs);
		
		ProcessBuilder pb = new ProcessBuilder(cmd);
		Process p = pb.start();
		p.getOutputStream().close();
		p.getInputStream().close();
		p.getErrorStream().close();
		return p;
	}

}
