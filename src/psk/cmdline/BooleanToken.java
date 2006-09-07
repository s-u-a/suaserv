// Copyright (c) 1998 Panos Kougiouris All Rights Reserved
package psk.cmdline;

/*
 * The Implemetation of Tokens for Strings
 *
 * @version 1.0, 11/02/1998
 * @author  Panos Kougiouris
 */

public class BooleanToken extends Token
{
	public BooleanToken(
		String a_name,
		String a_message,
		String a_environment_variable,
		int aTokenOptions,
		boolean a_def_value)
	{
		super(a_name, a_message, a_environment_variable, aTokenOptions);
		setDefaultValue(new Boolean(a_def_value));
	}

	public String type() {
		return "<Boolean>";
	}

	public boolean getValue() {
		return getValue(0);
	}

	public boolean getValue(int i) {
		return ((Boolean)m_values.elementAt(i)).booleanValue();
	}

	public Object toObject(String lexeme) {
		return new Boolean(lexeme.equalsIgnoreCase("true") || lexeme.equalsIgnoreCase("yes") || lexeme.equalsIgnoreCase("on") || lexeme.equals("1"));
	}
}
