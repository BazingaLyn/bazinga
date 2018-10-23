package org.bazinga;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liguolin
 * @create 2018-10-19 11:06
 **/
public class BazingaClientHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(BazingaClientHandler.class);



    private String too_long_context = "Iron Man is a 2008 American superhero film based on the Marvel Comics character of the same name, produced by Marvel Studios and distributed by Paramount Pictures.[N 1] It is the first film in the Marvel Cinematic Universe (MCU). The film was directed by Jon Favreau, with a screenplay by the writing teams of Mark Fergus and Hawk Ostby and Art Marcum and Matt Holloway. It stars Robert Downey Jr. as Tony Stark / Iron Man, alongside Terrence Howard, Jeff Bridges, Shaun Toub, and Gwyneth Paltrow. In Iron Man, Tony Stark, an industrialist and master engineer, builds a powered exoskeleton after a life-threatening incident and becomes the technologically advanced superhero Iron Man.\n" +
            "The film had been in development since 1990 at Universal Pictures, 20th Century Fox, or New Line Cinema at various times, before Marvel Studios reacquired the rights in 2006. Marvel put the project in production as its first self-financed film, with Paramount Pictures as its distributor. Favreau signed on as director, aiming for a naturalistic feel, and he chose to shoot the film primarily in California, rejecting the East Coast setting of the comics to differentiate the film from numerous superhero films set in New York City-esque environments. Filming began in March 2007 and concluded in June. During filming, the actors were free to create their own dialogue because pre-production was focused on the story and action. Rubber and metal versions of the armors, created by Stan Winston's company, were mixed with computer-generated imagery to create the title character.\n" +
            "Iron Man premiered in Sydney on April 14, 2008, and was released in the United States on May 2, 2008. The film grossed over $585 million on a $140 million budget, and received praise for its acting, particularly Downey's performance as Tony Stark, as well as the visual effects and action sequences. The American Film Institute selected the film as one of the ten best of the year. It was also nominated for two Academy Awards for Best Sound Editing and Best Visual Effects. Two sequels, Iron Man 2 and Iron Man 3, were released on May 7, 2010, and May 3, 2013, respectively.\n";



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info(">>>>>> BazingaClientHandler channelActive");

        // 粘包
        for(int i = 0; i< 100 ;i++){
            ctx.pipeline().writeAndFlush("hello world my name is lyncc");
        }

        // 拆包
        ctx.pipeline().writeAndFlush(too_long_context);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info(">>>>>> receive msg  {} from server",msg);
    }
}
