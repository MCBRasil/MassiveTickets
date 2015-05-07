package com.massivecraft.massivetickets.cmd;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.cmd.req.ReqHasPerm;
import com.massivecraft.massivetickets.Perm;
import com.massivecraft.massivetickets.entity.ARMPlayer;
import com.massivecraft.massivetickets.entity.MPlayer;

public class CmdTicketsCheat extends MassiveTicketsCommand
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public CmdTicketsCheat()
	{
		// Args
		this.addArg(ARMPlayer.getOnline(), "player", "you");
		
		// Requirements
		this.addRequirements(ReqHasPerm.get(Perm.CHEAT.node));
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void perform() throws MassiveException
	{
		// Args
		MPlayer mplayer = this.readArg(msender);
		
		// Force Sync
		mplayer.sync();
		
		// Apply
		mplayer.givePoint(null);
		
		// Inform
		msg("<g>You gave <white>%s <g>a point 4NORaisins!", mplayer.getDisplayName(sender));
	}
	
}
