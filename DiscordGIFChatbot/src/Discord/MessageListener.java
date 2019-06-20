package Discord;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import Yeet.Yoink;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.requests.Route;
import net.dv8tion.jda.core.requests.restaction.ChannelAction;
import net.dv8tion.jda.core.utils.Checks;
import net.dv8tion.jda.core.utils.cache.UpstreamReference;
import net.dv8tion.jda.core.entities.impl.GuildImpl;

public class MessageListener extends ListenerAdapter {

	public static String memeURL = null;
	public static String memeWebURL = null;
	Yoink yoink = new Yoink();
	
	public static void main(String[] args) throws LoginException {
		JDABuilder builder = new JDABuilder(AccountType.BOT);
		String token = "NTkxMTgzMTkwNzk4ODkzMDY2.XQtF0Q._dg7BQ4V51fjsl4g6Arv-uIQwPY";
		builder.setToken(token);

		builder.addEventListener(new MessageListener());

		builder.buildAsync();
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		

		if (event.isFromType(ChannelType.PRIVATE)) {
			System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(), event.getMessage().getContentDisplay());
		} else {
			System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(), event.getTextChannel().getName(),
					event.getMember().getEffectiveName(), event.getMessage().getContentDisplay());
		}
		if (!event.getAuthor().isBot()) {
			// System.out.println("User="+event.getMember().toString());
			String fancy = event.getMessage().getContentRaw();
			if (fancy.charAt(0) == '>') {
				event.getChannel().sendMessage(yoink.parse(fancy.substring(1).trim())).queue();
				event.getChannel().sendMessage(memeWebURL).queue();
			}
			if (fancy.equalsIgnoreCase("yeet")) {
				event.getChannel().sendMessage("YOINK!").queue();
			} else if (fancy.equalsIgnoreCase("yoink")) {
				event.getChannel().sendMessage("YEET!").queue();
			} else if (fancy.equals("lol") || contains(fancy, " lol") || contains(fancy, "lol ")) {
				event.getChannel().sendMessage("HAHAHAHA").queue();
			} else if (fancy.equals("noob")) {
				event.getChannel().sendMessage("NO YOU!").queue();
			} else if (fancy.equals("hi")) {
				event.getChannel().sendMessage("yo").queue();
			} else if (fancy.equalsIgnoreCase("shut up")) {
				event.getChannel().sendMessage("You first").queue();
			} else if (fancy.equalsIgnoreCase("I could do this all day")) {
				event.getChannel().sendMessage("Me too").queue();
			} else if (fancy.substring(0, 3).equalsIgnoreCase("I'm")) {
				event.getChannel().sendMessage("Hi " + fancy.substring(3) + ", I'm a fancy bot, pleased to meet you.")
						.queue();
			} else if (fancy.substring(0, 2).equalsIgnoreCase("Im")) {
				event.getChannel().sendMessage("Hi " + fancy.substring(2) + ", I'm a fancy bot, pleased to meet you.")
						.queue();
			} else if (fancy.substring(0, 4).equalsIgnoreCase("I am")) {
				event.getChannel().sendMessage("Hi " + fancy.substring(4) + ", I'm a fancy bot, pleased to meet you.")
						.queue();
			} else if (contains(fancy, "why")) {
				event.getChannel().sendMessage("Good question").queue();
			} else if (fancy.equalsIgnoreCase("geez")) {
				event.getChannel().sendMessage("Aw geez Rick").queue();
			} else if (contains(fancy, "cool")) {
				event.getChannel().sendMessage(
						"\"I'm up to here with cool, okay? I am so amazingly cool you could keep a side of meat in me for a month. I am so hip I have difficulty seeing over my pelvis.\" \n        --Douglas Adams")
						.queue();
			} else if (fancy.equals("ha, get destroyed")) {
				event.getChannel().sendMessage("Ha, get roasted").queue();
			} else if (fancy.equalsIgnoreCase("fancy")) {
				event.getChannel().sendMessage("Very fancy indeed").queue();
			} else if (contains(fancy, "gif") || contains(fancy, "bot")) {
				switch ((int) (Math.random() * 4)) {
				case 0:
					event.getChannel().sendMessage("I hear you talking about me behind my back!").queue();
					break;
				case 1:
					event.getChannel()
							.sendMessage("What did you just say about me " + event.getMember().getEffectiveName() + "?")
							.queue();
					break;
				case 2:
					event.getChannel().sendMessage("Oh hi " + event.getMember().getEffectiveName()
							+ ", I can hear what you're saying you know...").queue();
					break;
				case 3:
					event.getChannel().sendMessage("Did somebody say something about me?").queue();
					break;
				}
			}
		}
	}

	public boolean contains(String s1, String s2) {
		return s1.indexOf(s2) != -1 || s1.toLowerCase().indexOf(s2.toLowerCase()) != -1;
	}
}