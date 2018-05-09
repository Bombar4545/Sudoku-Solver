package sample;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author Ufuk BOMBAR
 * @since 08.05.2018 20:56
 */
public class Table
{
	public static final int SIZE = 3;
	private int[] data;
	private boolean[] available;

	public Table()
	{
		data = new int[ SIZE * SIZE * SIZE * SIZE ];
		available = new boolean[ SIZE * SIZE ];
		Arrays.fill( available, true );
	}

	public void resetVisited()
	{
		Arrays.fill( available, true );
	}

	public void resetAll()
	{
		Arrays.fill( data, 0 );
		Arrays.fill( available, true );
	}

	public void demo( int bRowBase, int bColBase )
	{
		for ( int i = 0; i < Table.SIZE; i++ )
			for ( int j = 0; j < Table.SIZE; j++ )
				setOn( bRowBase, bColBase, i, j, i * j + i + j );
	}

	public int get( int i )
	{
		return data[ i ];
	}

	public int getOn(  int bRow, int bCol , int row, int col )
	{
		return data[ bRow * SIZE * SIZE * SIZE + bCol * SIZE + row * SIZE * SIZE + col ];
	}

	public void setOn(  int bRow, int bCol , int row, int col, int val )
	{
		data[ bRow * SIZE * SIZE * SIZE + bCol * SIZE + row * SIZE * SIZE + col ] = val;
	}

	private boolean isAvailable( int bRowBase, int bColBase )
	{
		if ( bRowBase >= SIZE || bRowBase < 0 || bColBase >= SIZE || bColBase < 0 )
			return false;
		return available[ bRowBase * SIZE + bColBase ];
	}

	private void notAvailable( int bRowBase, int bColBase )
	{
		if ( bRowBase >= SIZE || bRowBase < 0 || bColBase >= SIZE || bColBase < 0 )
			return;
		available[ bRowBase * SIZE + bColBase ] = false;
	}

	private void fillRight( int bRowBase, int bColBase )
	{
		if ( bColBase >= SIZE - 1 ) // PROBLEM
			return;

		for ( int i = 0; i < SIZE; i++ )
		{
			for ( int j = 0; j < SIZE; j++ ) // j : columns, i : rows
			{
				int value = getOn( bRowBase, bColBase, ( i + 1 ) % SIZE, j );
				setOn( bRowBase, bColBase + 1, i, j, value );
			}
		}

	}

	private void fillDown( int bRowBase, int bColBase )
	{
		if ( bRowBase >= SIZE - 1 ) // PROBLEM
			return;

		for ( int i = 0; i < SIZE; i++ )
		{
			for ( int j = 0; j < SIZE; j++ ) // j : columns, i : rows
			{
				int value = getOn( bRowBase, bColBase, i, ( j + 1 ) % SIZE );
				setOn( bRowBase + 1, bColBase, i, j, value );
			}
		}

	}

	private void fillLeft( int bRowBase, int bColBase )
	{
		if ( bColBase < 1 ) // PROBLEM
			return;

		for ( int i = 0; i < SIZE; i++ )
		{
			for ( int j = 0; j < SIZE; j++ ) // j : columns, i : rows
			{
				int value = getOn( bRowBase, bColBase, ( i - 1 + SIZE ) % SIZE, j );
				setOn( bRowBase, bColBase - 1, i, j, value );
			}
		}

	}

	private void fillUp( int bRowBase, int bColBase )
	{
		if ( bRowBase < 1 ) // PROBLEM
			return;

		for ( int i = 0; i < SIZE; i++ )
		{
			for ( int j = 0; j < SIZE; j++ ) // j : columns, i : rows
			{
				int value = getOn( bRowBase, bColBase, i, ( j - 1 + SIZE ) % SIZE );
				setOn( bRowBase - 1, bColBase, i, j, value );
			}
		}

	}

	public void fillOn( int bRowBase, int bColBase )
	{
		notAvailable( bRowBase, bColBase);

		boolean left = isAvailable( bRowBase, bColBase - 1 );
		boolean right = isAvailable( bRowBase, bColBase + 1 );
		boolean up = isAvailable( bRowBase - 1, bColBase);
		boolean down = isAvailable( bRowBase + 1, bColBase);

		// ONE WAY OR
		/*
		if ( left )
		{
			fillLeft( bRowBase, bColBase );
			notAvailable( bRowBase, bColBase - 1 );
			fillOn( bRowBase, bColBase - 1 );
		}
		if ( right )
		{
			fillRight( bRowBase, bColBase );
			notAvailable( bRowBase, bColBase + 1 );
			fillOn( bRowBase, bColBase + 1 );
		}
		if ( up )
		{
			fillUp( bRowBase, bColBase );
			notAvailable( bRowBase - 1, bColBase);
			fillOn( bRowBase - 1, bColBase);
		}
		if ( down )
		{
			fillDown( bRowBase, bColBase );
			notAvailable( bRowBase + 1, bColBase);
			fillOn( bRowBase + 1, bColBase);
		}
		// */
		// OTHER WAY
		// /*


		if ( left )
		{
			fillLeft( bRowBase, bColBase );
			notAvailable( bRowBase, bColBase - 1 );
		}
		if ( right )
		{
			fillRight(bRowBase, bColBase);
			notAvailable( bRowBase, bColBase + 1 );
		}
		if ( up )
		{
			fillUp(bRowBase, bColBase);
			notAvailable( bRowBase - 1, bColBase);
		}
		if ( down )
		{
			fillDown(bRowBase, bColBase);
			notAvailable( bRowBase + 1, bColBase);
		}

		if ( left )
			fillOn( bRowBase, bColBase - 1 );
		if ( right )
			fillOn( bRowBase, bColBase + 1 );
		if ( up )
			fillOn( bRowBase - 1, bColBase);
		if ( down )
			fillOn( bRowBase + 1, bColBase);
		// */
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for ( int i = 0; i < SIZE * SIZE; i++ )
		{
			sb.append( i % SIZE == 0 ? "\n\n" : "\n" );
			for ( int j = 0; j < SIZE * SIZE; j++ ) // j : columns, i : rows
			{
				sb.append(  j % SIZE == 0 ? "   " : " " );
				sb.append( data[ i * SIZE * SIZE + j ] );
			}
		}
		return sb.toString();
	}



}
