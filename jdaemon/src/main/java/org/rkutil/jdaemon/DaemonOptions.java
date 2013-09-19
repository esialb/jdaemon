package org.rkutil.jdaemon;

import org.apache.commons.cli.Options;

public class DaemonOptions extends Options {
	public static final String FOREGROUND = "foreground";
	public static final String STDOUT = "std-out";
	public static final String STDERR = "std-err";
	public static final String STDIN = "std-in";
	public static final String REDIRECT = "redirect";
	public static final String MAIN = "main";
	public static final String HELP = "help";
	
	public DaemonOptions() {
		addOption("f", "foreground", false, "run the daemon in the foreground (do not fork)");
		addOption("o", "std-out", true, "file to write STDOUT to");
		addOption("e", "std-err", true, "file to write STDERR to");
		addOption("i", "std-in", true, "file to read STDIN from");
		addOption("r", "redirect", false, "redirect STDERR to STDOUT");
		addOption("m", "main", true, "class name of the main class");
		addOption("h", "help", false, "show command-line usage");
	}
	
	private static void p(String s) {
		System.out.println(s);
	}
	
	public static void help() {
		p("jdaemon launcher usage:");
		p("java [<vm_arg>...] [<jd_arg>...] [-- <tool_arg>...]");
		p("");
		p("vm_arg:   Java virtual machine args, including a way to invoke org.rkutil.jdaemon.Daemon");
		p("jd_arg:   jdaemon arguments:");
		p("          -f --foreground            Launch the tool main class in the foreground");
		p("          -s --std-out FILE          Write STDOUT to FILE");
		p("          -e --std-err FILE          Write STDERR to FILE");
		p("          -i --std-in FILE           Read STDIN from FILE");
		p("          -f --redirect              Redirect STDERR to STDOUT (ignore -e if present)");
		p("          -m --main CLASSNAME        Invoke CLASSNAME as the tool main class");
		p("          -h --help                  Show this help");
		p("--:       --                         Separator to mark end of jdaemon args and beginning of tool args");
		p("tool_arg: Arguments to the tool being launched");
	}
}
