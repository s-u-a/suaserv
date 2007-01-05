package de.cdauth.sua.suaserv;

public class ProductionAmount extends RessAmount
{
	private long m_power;

	public ProductionAmount(long a_carbon, long a_aluminium, long a_wolfram, long a_radium, long a_tritium, long a_power)
	{
		super(a_carbon, a_aluminium, a_wolfram, a_radium, a_tritium);
		m_power = a_power;
	}

	public ProductionAmount(long a_carbon, long a_aluminium, long a_wolfram, long a_radium, long a_tritium)
	{
		this(a_carbon, a_aluminium, a_wolfram, a_radium, a_tritium, 0);
	}

	public ProductionAmount(long a_carbon, long a_aluminium, long a_wolfram, long a_radium)
	{
		this(a_carbon, a_aluminium, a_wolfram, a_radium, 0, 0);
	}

	public ProductionAmount()
	{
		this(0, 0, 0, 0, 0, 0);
	}

	public long getPower()
	{
		return m_power;
	}

	public void setPower(long a_power)
	{
		m_power = a_power;
	}
}