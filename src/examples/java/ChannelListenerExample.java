/**
 *    Copyright 2015 Austin Keener & Michael Ritter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import net.dv8tion.jda.JDA;
import net.dv8tion.jda.JDABuilder;
import net.dv8tion.jda.events.channel.text.*;
import net.dv8tion.jda.events.channel.voice.*;
import net.dv8tion.jda.hooks.ListenerAdapter;
import org.json.JSONException;
import org.json.JSONObject;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ChannelListenerExample extends ListenerAdapter
{
    public static void main(String[] args)
    {
        JSONObject config = getConfig();
        try
        {
            JDA api = new JDABuilder()
                    .setEmail(config.getString("email"))
                    .setPassword(config.getString("password"))
                    .addListener(new ChannelListenerExample())
                    .build();
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("The config was not populated. Please enter an email and password.");
        }
        catch (LoginException e)
        {
            System.out.println("The provided email / password combination was incorrect. Please provide valid details.");
        }
    }

    // ------------------------------
    // ----- TextChannel Events -----
    // ------------------------------
    @Override
    public void onTextChannelCreate(TextChannelCreateEvent event)
    {
        System.out.println("A TextChannel named: " + event.getChannel().getName() + " was created in guild: " + event.getGuild().getName());
    }

    @Override
    public void onTextChannelDelete(TextChannelDeleteEvent event)
    {
        System.out.println("A TextChannel named: " + event.getChannel().getName() + " was deleted from guild: " + event.getGuild().getName());
    }

    @Override
    public void onTextChannelUpdateName(TextChannelUpdateNameEvent event)
    {
        System.out.println("TextChannel " + event.getOldName() + " was renamed: " + event.getChannel().getName() + " in guild " + event.getGuild().getName());
    }

    @Override
    public void onTextChannelUpdateTopic(TextChannelUpdateTopicEvent event)
    {
        System.out.println("The " + event.getChannel().getName() + " TextChannel's topic just from\n" + event.getOldTopic() + "\n to\n" + event.getChannel().getTopic());
    }

    @Override
    public void onTextChannelUpdatePosition(TextChannelUpdatePositionEvent event)
    {
        System.out.println("The position of " + event.getChannel().getName() + " TextChannl just moved from " + event.getOldPosition() + " to " + event.getChannel().getPosition());
        System.out.println("Be sure to update your channel lists!");
    }

    @Override
    public void onTextChannelUpdatePermissions(TextChannelUpdatePermissionsEvent event)
    {
        System.out.println("TextChannel Permissions changed. There are a lot of details in this event and I'm too lazy to show them all. Just read the Javadoc ;_;");
    }

    // ------------------------------
    // ---- VoiceChannel Events -----
    // ------------------------------
    @Override
    public void onVoiceChannelCreate(VoiceChannelCreateEvent event)
    {
        System.out.println("A VoiceChannel named: " + event.getChannel().getName() + " was created in guild: " + event.getGuild().getName());
    }

    @Override
    public void onVoiceChannelDelete(VoiceChannelDeleteEvent event)
    {
        System.out.println("A VoiceChannel named: " + event.getChannel().getName() + " was deleted from guild: " + event.getGuild().getName());
    }

    @Override
    public void onVoiceChannelUpdateName(VoiceChannelUpdateNameEvent event)
    {
        System.out.println("VoiceChannel " + event.getOldName() + " was renamed: " + event.getChannel().getName() + " in guild " + event.getGuild().getName());
    }

    //No onVoiceChannelUpdateTopic method because VoiceChannels don't have Topics.

    @Override
    public void onVoiceChannelUpdatePosition(VoiceChannelUpdatePositionEvent event)
    {
        System.out.println("The position of " + event.getChannel().getName() + " VoiceChannl just moved from " + event.getOldPosition() + " to " + event.getChannel().getPosition());
        System.out.println("Be sure to update your channel lists!");
    }

    @Override
    public void onVoiceChannelUpdatePermissions(VoiceChannelUpdatePermissionsEvent event)
    {
        System.out.println("VoiceChannel Permissions changed. There are a lot of details in this event and I'm too lazy to show them all. Just read the Javadoc ;_;");
    }

    //Simple config system to make life easier. THIS IS NOT REQUIRED FOR JDA.
    private static JSONObject getConfig()
    {
        File config = new File("config.json");
        if (!config.exists())
        {
            try
            {
                Files.write(Paths.get(config.getPath()),
                        new JSONObject()
                                .put("email", "")
                                .put("password", "")
                                .put("proxyHost", "")
                                .put("proxyPort", 8080)
                                .toString(4).getBytes());
                System.out.println("config.json created. Populate with login information.");
                System.exit(0);
            }
            catch (JSONException | IOException e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            return new JSONObject(new String(Files.readAllBytes(Paths.get(config.getPath())), "UTF-8"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
