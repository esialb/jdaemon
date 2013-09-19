package org.rkutil.jdaemon;

import org.apache.commons.cli.Options;

public class DaemonOptions extends Options {
	public static final String FOREGROUND = "foreground";
	public static final String QUIET = "quiet";
	public static final String STDOUT = "std-out";
	public static final String STDERR = "std-err";
	public static final String STDIN = "std-in";
	public static final String REDIRECT = "redirect";
	public static final String MAIN = "main";
	public static final String METAINF = "meta-inf";
	public static final String PIDFILE = "pid-file";
	public static final String HELP = "help";
	
	public DaemonOptions() {
		addOption("f", FOREGROUND, false, "run the daemon in the foreground (do not fork)");
		addOption("q", QUIET, false, "discard STDOUT and STDERR");
		addOption("o", STDOUT, true, "file to write STDOUT to");
		addOption("e", STDERR, true, "file to write STDERR to");
		addOption("i", STDIN, true, "file to read STDIN from");
		addOption("r", REDIRECT, false, "redirect STDERR to STDOUT");
		addOption("m", MAIN, true, "class name of the main class");
		addOption("M", METAINF, false, "read main class from META-INF/jdaemon.ini");
		addOption("p", PIDFILE, true, "file to write the tool PID to");
		addOption("h", HELP, false, "show command-line usage");
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
		p("          -q --quiet                 Discard STDOUT and STDERR");
		p("          -s --std-out FILE          Write STDOUT to FILE (overrides -q)");
		p("          -e --std-err FILE          Write STDERR to FILE (overrides -q)");
		p("          -i --std-in FILE           Read STDIN from FILE");
		p("          -f --redirect              Redirect STDERR to STDOUT (overrides -e)");
		p("          -m --main CLASSNAME        Invoke CLASSNAME as the tool main class");
		p("          -M --meta-inf              Invoke main class specified in META-INF/jdaemon.ini on the classpath");
		p("          -p --pid-file FILE         Write the tool's PID to FILE");
		p("          -h --help                  Show this help");
		p("--:       --                         Separator to mark end of jdaemon args and beginning of tool args");
		p("tool_arg: Arguments to the tool being launched");
	}
}
