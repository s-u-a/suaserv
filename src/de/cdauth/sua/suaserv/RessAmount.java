package de.cdauth.sua.suaserv;

public class RessAmount
{
	private long m_carbon;
	private long m_aluminium;
	private long m_wolfram;
	private long m_radium;
	private long m_tritium;

	public RessAmount(long a_carbon, long a_aluminium, long a_wolfram, long a_radium, long a_tritium)
	{
		m_carbon = a_carbon;
		m_aluminium = a_aluminium;
		m_wolfram = a_wolfram;
		m_radium = a_radium;
		m_tritium = a_tritium;
	}

	public RessAmount(long a_carbon, long a_aluminium, long a_wolfram, long a_radium)
	{
		this(a_carbon, a_aluminium, a_wolfram, a_radium, 0);
	}

	public RessAmount()
	{
		this(0, 0, 0, 0, 0);
	}

	public long getCarbon()
	{
		return m_carbon;
	}

	public void setCarbon(long a_amount)
	{
		m_carbon = a_amount;
	}

	public long getAluminium()
	{
		return m_aluminium;
	}

	public void setAluminium(long a_amount)
	{
		m_aluminium = a_amount;
	}

	public long getWolfram()
	{
		return m_wolfram;
	}

	public void setWolfram(long a_amount)
	{
		m_aluminium = a_amount;
	}

	public long getRadium()
	{
		return m_radium;
	}

	public void setRadium(long a_amount)
	{
		m_radium = a_amount;
	}

	public long getTritium()
	{
		return m_tritium;
	}

	public void setTritium(long a_amount)
	{
		m_tritium = a_amount;
	}
}