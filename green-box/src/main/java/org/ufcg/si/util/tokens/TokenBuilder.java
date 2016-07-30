package org.ufcg.si.util.tokens;

import org.ufcg.si.models.User;

public interface TokenBuilder {
	public String build(User user);
}
