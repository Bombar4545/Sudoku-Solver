package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import static sample.Table.*;

public class Main extends Application
{
	public static Main main;

	private class SudokuButton extends Button
	{
		private int bRow;
		private int bCol;
		private int row;
		private int col;

		public SudokuButton( int bRow, int bCol, int row, int col )
		{
			super();
			this.bCol = bCol;
			this.bRow = bRow;
			this.row = row;
			this.col = col;
			setMinSize( 60, 60 );

			if ( (( bRow == 0 || bRow == 2) && (bCol == 0 || bCol == 2) ) || ( bCol == 1 && bRow == 1 ) )
				setStyle("-fx-base: #ffffff;");
			else
				setStyle("-fx-base: #aaaaaa;");
		}

		public int getbCol()
		{
			return bCol;
		}

		public int getbRow()
		{
			return bRow;
		}

		public int getCol()
		{
			return col;
		}

		public int getRow()
		{
			return row;
		}
	}

	private SudokuButton[] buttons;
	// private GridPane[] panes;
	private SudokuButton focusedButton;
	private Table table;

	/*
	@Override
	public void start( Stage primaryStage )
	{
		buttons = new SudokuButton[ SIZE * SIZE * SIZE * SIZE ];
		panes = new GridPane[ SIZE * SIZE ];

		EventHandler<ActionEvent> sudokuButtonClicked = (event ) ->
		{
			focusedButton = (SudokuButton ) event.getSource();
			updateButtons();
		};

		EventHandler<KeyEvent> keyPress = (event ) ->
		{
			SudokuButton tmp = ( SudokuButton ) event.getSource();
			if ( event.getCode() == KeyCode.ENTER )
			{
				table.fillOn(focusedButton.getbRow(), focusedButton.getbCol());
				updateButtons();
			}
			else if ( "123456789".contains( event.getText() ))
			{

				table.setOn( tmp.getbRow(), tmp.getbCol(), tmp.getRow(), tmp.getCol(), Integer.parseInt( event.getText() ) );

				updateButtons();
			}
		};

		for ( int bi = 0; bi < SIZE; bi++ )
			for ( int bj = 0; bj < SIZE; bj++ )
				for ( int i = 0; i < SIZE; i++ )
					for ( int j = 0; j < SIZE; j++ ) // bi: bRow, bj: bCol, i: row, j: col
					{
						int index = bi * SIZE * SIZE * SIZE + bj * SIZE + i * SIZE * SIZE + j;

						buttons[ index ] = new SudokuButton( bi, bj, i, j );
						buttons[ index ].setOnKeyPressed( keyPress );
						buttons[ index ].setOnAction( sudokuButtonClicked );

						panes[ bi * SIZE + bj ] = new GridPane();
						panes[ bi * SIZE + bj ].setVgap( 2 );
						panes[ bi * SIZE + bj ].setHgap( 2 );
						panes[ bi * SIZE + bj ].add( buttons[ index ], i, j );
					}

		Group root = new Group();
		GridPane container = new GridPane();
		container.setMinSize(400, 400);
		updateButtons();

		for ( int bi = 0; bi < SIZE; bi++ )
			for ( int bj = 0; bj < SIZE; bj++ )
				container.add( panes[ bi * SIZE + bj ], bi, bj );

		root.getChildren().add( container);

		primaryStage.setTitle("Sudoku Solver");
		primaryStage.setScene( new Scene(root));
		primaryStage.show();



	}

	// */
	// /*

    @Override
    public void start(Stage primaryStage)
	{
		table = new Table();
		main = this;

		buttons = new SudokuButton[ SIZE * SIZE * SIZE * SIZE ];

		EventHandler<ActionEvent> sudokuButtonClicked = ( event ) ->
		{
			focusedButton = (SudokuButton ) event.getSource();
		};

		EventHandler<KeyEvent> keyPress = ( event ) ->
		{
			SudokuButton tmp = ( SudokuButton ) event.getSource();
			if ( event.getCode() == KeyCode.ENTER )
			{
				table.fillOn(focusedButton.getbRow(), focusedButton.getbCol());
				updateButtons();
				table.resetVisited();
			}
			else if ( event.getCode() == KeyCode.BACK_SPACE )
			{
				table.resetAll();
				updateButtons();
				table.resetVisited();
			}
			else if ( "123456789".contains( event.getText() ))
			{

				table.setOn( tmp.getbRow(), tmp.getbCol(), tmp.getRow(), tmp.getCol(), Integer.parseInt( event.getText() ) );

				updateButtons();
				table.resetVisited();
			}
		};

		Group root = new Group();
		GridPane pane = new GridPane();

		//-------------------------------------------------------------------- BAD PRACTICE :/

		for ( int bi = 0; bi < SIZE; bi++ )
			for ( int bj = 0; bj < SIZE; bj++ )
				for ( int i = 0; i < SIZE; i++ )
					for ( int j = 0; j < SIZE; j++ ) // bi: bRow, bj: bCol, i: row, j: col
						buttons[ bi * SIZE * SIZE * SIZE + bj * SIZE + i * SIZE * SIZE + j ] = new SudokuButton( bi, bj, i, j );

		for ( int i = 0; i < buttons.length; i++ )
		{
			buttons[ i ].setOnAction( sudokuButtonClicked );
			buttons[ i ].setOnKeyPressed( keyPress );
		}

		updateButtons();

		//---------------------------------------------------------------------

		for ( int i = 0; i < SIZE * SIZE; i++ )
			for ( int j = 0; j < SIZE * SIZE; j++ )
				pane.add( buttons[ i * SIZE * SIZE + j ], j, i );

		root.getChildren().add( pane );

        primaryStage.setTitle("Sudoku Application by Ufuk Bombar");
        primaryStage.setScene( new Scene( root ) );
        primaryStage.show();
    }

    public void updateButtons()
	{
		for ( int i = 0; i < buttons.length; i++ )
		{
			String text = table.get( i ) == 0 ? "" : table.get( i ) + "";
			buttons[ i ].setText( text );
		}

	}

	// */

    public static void main(String[] args)
    {
        launch( args );
    }
}
