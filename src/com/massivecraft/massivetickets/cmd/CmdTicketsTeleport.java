package com.massivecraft.massivetickets.cmd;

import java.util.List;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.mixin.MixinTeleport;
import com.massivecraft.massivecore.mixin.TeleporterException;
import com.massivecraft.massivecore.teleport.DestinationPlayer;
import com.massivecraft.massivetickets.Perm;
import com.massivecraft.massivetickets.entity.TypeMPlayer;
import com.massivecraft.massivetickets.entity.MConf;
import com.massivecraft.massivetickets.entity.MPlayer;

public class CmdTicketsTeleport extends MassiveTicketsCommand
{
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	private static CmdTicketsTeleport i = new CmdTicketsTeleport() { @Override public List<String> getAliases() { return MConf.get().aliasesOuterTicketsTeleport; } };
	public static CmdTicketsTeleport get() { return i; }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public CmdTicketsTeleport()
	{
		// Parameters
		this.addParameter(TypeMPlayer.getAny(), "player");
		
		// Requirements
		this.addRequirements(RequirementHasPerm.get(Perm.TELEPORT));
		this.addRequirements(RequirementIsPlayer.get());
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public List<String> getAliases()
	{
		return MConf.get().aliasesInnerTicketsTeleport;
	}
	
	@Override
	public void perform() throws MassiveException
	{
		// Args
		MPlayer mplayer = this.readArg();
		
		// If not a player, return
		if ( ! mplayer.isPlayer()) return;
		
		// Try teleport
		try
		{
			MixinTeleport.get().teleport(me, new DestinationPlayer(mplayer));
		}
		catch (TeleporterException e)
		{
			me.sendMessage(e.getMessage());
		}
	}
	
}
