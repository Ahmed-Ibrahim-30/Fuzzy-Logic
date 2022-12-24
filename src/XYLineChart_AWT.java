import java.awt.Color;
import java.awt.BasicStroke;
import java.util.ArrayList;
import java.util.Arrays;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

public class XYLineChart_AWT extends ApplicationFrame {
    ArrayList<FuzzySet>fuzzySets;
    public XYLineChart_AWT(String chartTitle , ArrayList<FuzzySet>fuzzySets) {
        super(chartTitle);
        this.fuzzySets=fuzzySets;
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle ,
                "X-axis" ,
                "Y-axis" ,
                createDataset() ,
                PlotOrientation.VERTICAL ,
                true , true , false);

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        final XYPlot plot = xylineChart.getXYPlot( );

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        ArrayList<Color>colors=new ArrayList<>(Arrays.asList(Color.magenta,Color.blue,Color.GREEN,
                Color.YELLOW,Color.RED,Color.BLACK,Color.CYAN,Color.pink,Color.ORANGE));
        for(int i=0;i< fuzzySets.size();i++){
            renderer.setSeriesPaint( i , colors.get(i));
            renderer.setSeriesStroke( i , new BasicStroke( 4.0f ) );
        }
        plot.setRenderer( renderer );
        setContentPane( chartPanel );
    }

    private XYDataset createDataset( ) {
        final XYSeriesCollection dataset = new XYSeriesCollection( );
        for (FuzzySet fuzzySet:fuzzySets){
            final XYSeries obj = new XYSeries( fuzzySet.getName() );
            for (int i = 0; i < fuzzySet.values.size(); i++) {
                if (i == 0 || i == fuzzySet.values.size()-1) {
                    obj.add( (double)fuzzySet.values.get(i) , 0.0 );
                }else {
                    obj.add( (double)fuzzySet.values.get(i) , 1.0 );
                }
            }
            dataset.addSeries(obj);
        }
        return dataset;
    }
}