package com.gradleware.fun

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class Fibonacci extends DefaultTask { 
	
	@Input
	int arg
	long result=-1
	Task task1
	Task task2
	
	@TaskAction
	def fib() {
		if (arg<=1){
			result=arg
			return
		}
		result = task1.result + task2.result
	}
	
	static Task wireUpFibonacciGraph(int N, Project project){
		def tasks = project.tasks
		for (n in 0..N){
			Task t = tasks.add("fib${n}",Fibonacci)
			t.configure {
				arg=n
				if (n>=2){
				    task1 = tasks.findByPath("fib${n-1}")
				    task2 = tasks.findByPath("fib${n-2}")
					dependsOn task1
					dependsOn task2
				}
			}
		}
		tasks.findByPath("fib${N}")
	}
}

