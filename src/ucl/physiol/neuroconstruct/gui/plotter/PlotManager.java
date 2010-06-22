/**
 *  neuroConstruct
 *  Software for developing large scale 3D networks of biologically realistic neurons
 * 
 *  Copyright (c) 2009 Padraig Gleeson
 *  UCL Department of Neuroscience, Physiology and Pharmacology
 *
 *  Development of this software was made possible with funding from the
 *  Medical Research Council and the Wellcome Trust
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package ucl.physiol.neuroconstruct.gui.plotter;

import java.util.*;

import java.awt.*;

import ucl.physiol.neuroconstruct.utils.*;
import ucl.physiol.neuroconstruct.project.*;

/**
 * Central point for controlling references to plot frames, to facilitate plotting multiple DataSets
 * in each PlotFrame.
 *
 * @author Padraig Gleeson
 *  
 */


public class PlotManager
{
    ClassLogger logger = new ClassLogger("PlotManager");

    private static Hashtable<String, PlotterFrame> existingPlotFrames 
                                    = new Hashtable<String, PlotterFrame>();

    /**
     * Reference to current project, needed for interaction with saved projects
     */
    private static Project project = null;


    /**
     * Creates a new PlotterFrame if one with that title isn't present, and if it is returns the
     * existing one (and so allows 2 or more data sets in same frame...)
     */
    public static PlotterFrame getPlotterFrame(String reference)
    {
        return getPlotterFrame(reference, false, true);
    }

    public static PlotterFrame getPlotterFrame(String reference,
                                               boolean setVisible)
    {
        return getPlotterFrame(reference, false, setVisible);
    }


    public static PlotterFrame getPlotterFrame(String reference, 
                                                boolean standalone, 
                                                boolean setVisible)
    {
        GuiUtils.setShowInfoGuis(true); // In case started at command line & Plot frame explicitly called => show dialogs for warnings etc.

        if (existingPlotFrames.containsKey(reference))
        {
            PlotterFrame frame = existingPlotFrames.get(reference);
            return frame;
        }
        PlotterFrame frame = new PlotterFrame(reference, project, standalone);
        existingPlotFrames.put(reference, frame);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

        frame.setVisible(setVisible);

        return frame;
    }


    public static Vector<String> getPlotterFrameReferences()
    {
        Vector<String> allRefs = new Vector<String>();
        Enumeration<String> enumeration = existingPlotFrames.keys();
        while (enumeration.hasMoreElements()) {
            allRefs.add(enumeration.nextElement());

        }
        return allRefs;
    }

    public static void plotFrameClosing(String ref)
    {
        existingPlotFrames.remove(ref);
    }

    public static void setCurrentProject(Project currProject)
    {
        project = currProject;
    }



}
