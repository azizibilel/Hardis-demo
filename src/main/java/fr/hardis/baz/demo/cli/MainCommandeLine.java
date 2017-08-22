package fr.hardis.baz.demo.cli;

import javax.annotation.PostConstruct;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import fr.hardis.baz.demo.service.ConversionService;

@Component
@Profile("!test")
public class MainCommandeLine implements CommandLineRunner {
	
	private static final Logger LOG = LoggerFactory.getLogger(MainCommandeLine.class);
	
	@Autowired
	private ConversionService convertService;
	

    private Options options;
    private CommandLineParser parser;
    private CommandLine cmd;
    private HelpFormatter formatter;

    /**
     * Initiates the sub-command
     */
    @PostConstruct
    public void init () {
        options = new Options();
        parser = new DefaultParser();
        formatter = new HelpFormatter();

        final Option fileInOption = Option.builder("i")
                .argName("in")
                .hasArg()
                .desc("File to be analyzed")
                .build();
        
        final Option formatOption = Option.builder("f")
                .argName("format")
                .hasArg()
                .desc("Output format XML/JSON")
                .build();
        
        final Option fileOutOption = Option.builder("o")
                .argName("out")
                .hasArg()
                .desc("converted File")
                .build();

        options.addOption(fileInOption);
        options.addOption(formatOption);
        options.addOption(fileOutOption);
    }

    @Override
    public void run(String... args) throws Exception {
    	if(args.length == 0) {
    		formatter.printHelp("<cmd>", options);
    	}else {
    		try {
                cmd = parser.parse(options, args);
            } catch (UnrecognizedOptionException | MissingArgumentException  e) {
                formatter.printHelp("sample", options);
                System.exit(1);
            }

            if (cmd.hasOption("i") && !cmd.getOptionValue("i").isEmpty()
            		&& cmd.hasOption("f") && !cmd.getOptionValue("f").isEmpty()
            		&& cmd.hasOption("o") && !cmd.getOptionValue("o").isEmpty()) {
            	final StringBuilder str= new StringBuilder();
            	str.append("Demo démarré").append("\n");
                str.append("-- i file:").append(cmd.getOptionValue("i")).append("\n");
                str.append("-- f ").append(cmd.getOptionValue("f")).append("\n");
                str.append("-- o file : ").append(cmd.getOptionValue("o"));
                LOG.info(str.toString());
                
                convertService.convert(cmd.getOptionValue("i"), cmd.getOptionValue("f"), cmd.getOptionValue("o"));
                
            }
    		
    	}
    }
}