package org.rts.rtsprofile;

import java.util.Properties;
import org.rts.base.Scrapper;
import org.rts.base.ScrapperProfile;
import org.rts.impl.PastieImpl;

public class PastebincaProfile implements ScrapperProfile{
	public String getName() {
		// TODO Auto-generated method stub
		//check with time if pastebin.ca comess up with different results browsers and mine are not matching.. need to identify what is that extra parameter needed man
		return "Pastebinca";
	}

	public Scrapper newInstance(Properties properties) {
		// TODO Auto-generated method stub
		PastieImpl pastie=new PastieImpl();
		pastie.initScrapper(properties);
		return pastie;
	}

}
