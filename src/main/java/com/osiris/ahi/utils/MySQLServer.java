/*
 *  Copyright Osiris Team
 *  All rights reserved.
 *
 *  This software is licensed work.
 *  Please consult the file "LICENSE" for details.
 */

package com.osiris.ai.utils;

import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;

import java.io.File;
import java.util.Properties;

public class MySQLServer {
    private HsqlProperties properties;
    private Server server;
    private boolean running = false;

    private static Properties getDefaultSQLProps() {
        Properties p = new Properties();
        p.setProperty("server.database.0", "file:"+System.getProperty("user.dir")+File.separator+"db"+File.separator+"defaultDB");
        p.setProperty("server.dbname.0", "defaultDB");
        p.setProperty("server.remote_open", "true");
        p.setProperty("hsqldb.reconfig_logging", "false");
        return p;
    }

    /**
     * Creates and opens a new MySQL server
     * (or loads already existing) at the default path: 'user dir'/db
     */
    public MySQLServer(){
        this(getDefaultSQLProps());
    }

    public MySQLServer(Properties props) {
        properties = new HsqlProperties(props);
        server = new Server();
        try{
            server.setProperties(properties);
            server.start();
            running = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
