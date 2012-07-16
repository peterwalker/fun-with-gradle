package com.gradleware.fun

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class Factorial extends DefaultTask {
	
	@Input
	int arg
	BigInteger result=-1
	Task task1
	
	@TaskAction
	def fib() {
		if (arg<=1){
            result=1
			return
		}
		result = arg * task1.result
	}
	
	static Task wireUpFactorialGraph(int N, Project project){
		def tasks = project.tasks
		for (n in 0..N){
			Task t = tasks.add("fac${n}",Factorial)
			t.configure {
				arg=n
				if (n>=2){
				    task1 = tasks.findByPath("fac${n-1}")
					dependsOn task1
				}
			}
		}
		tasks.findByPath("fac${N}")
	}
}

