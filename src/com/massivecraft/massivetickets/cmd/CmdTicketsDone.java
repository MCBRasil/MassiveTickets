package com.massivecraft.massivetickets.cmd;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.cmd.req.ReqHasPerm;
import com.massivecraft.massivetickets.Perm;
import com.massivecraft.massivetickets.entity.ARMPlayer;
import com.massivecraft.massivetickets.entity.MConf;
import com.massivecraft.massivetickets.entity.MPlayer;

public class CmdTicketsDone extends MassiveTicketsCommand
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public CmdTicketsDone()
	{
		// Args
		this.addArg(ARMPlayer.getOnline(), "player", "you");
		
		// Requirements
		this.addRequirements(ReqHasPerm.get(Perm.DONE.node));
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void perform() throws MassiveException
	{
		// Args
		MPlayer ticket = this.readArg(msender);
		
		// Force Sync
		ticket.sync();
		
		// Is there a ticket?
		if (!ticket.hasMessage())
		{
			msg("<white>%s <b>has not created a ticket.", ticket.getDisplayName(null));
			return;
		}
		
		// Is this ticket created by me or someone else?
		boolean other = (ticket != msender);
		if (other && !Perm.DONE_OTHER.has(sender, true)) return;
		
		// Now mark it as done
		ticket.markAsDone(msender);
		
		// React
		MConf.get().getDoneReaction().run(ticket.getModeratorId(), ticket.getId());
	}
	
}
