def cli = new CliBuilder(usage: 'tomcat start|stop|restart|status|log')
cli.start('start tomcat')
cli.stop('stop tomcat')
cli.restart('restart tomcat')
cli.status('check on tomcat running status')
cli.help('prints usage')

def tomcatService = 'sudo /sbin/service tomcat '
def start = "${tomcatService} start"
def stop = "${tomcatService} stop"
def restart = "${stop} && ${start}"
def status = "${tomcatService} status"

def command

def options = cli.parse(args)
switch (options.arguments()[0]) {
    case 'start':
        command = start
        break
    case 'stop':
        command = stop
        break
    case 'restart':
        command = restart
        break
    case 'status':
        command = status
        break
    default:
        println cli.usage()
        return
}

def process = command.execute()
process.waitFor()
def returnCode = process.exitValue()
if(returnCode == 0) {
    println process.in.text
} else {
    println process.err.text
}
return returnCode